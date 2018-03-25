import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import org.json.JSONObject;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RecordProcessor implements IRecordProcessor {
    private String kinesisShardId;
    // Backoff and retry settings
//    private static final long BACKOFF_TIME_IN_MILLIS = 1000L;
    private static final long BACKOFF_TIME_IN_MILLIS = 250L;
    private static final int NUM_RETRIES = 2;

    // Checkpoint about once a minute
    private static final long CHECKPOINT_INTERVAL_MILLIS = 60000L;
    private long nextCheckpointTimeInMillis;

    private long lastFired;
    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();


    /**
     * Checkpoint with retries.
     *
     * @param checkpointer
     */
    private void checkpoint(IRecordProcessorCheckpointer checkpointer) {
        System.out.println("Checkpointing shard " + kinesisShardId);
        for (int i = 0; i < NUM_RETRIES; i++) {
            try {
                checkpointer.checkpoint();
                break;
            } catch (ShutdownException se) {
                // Ignore checkpoint if the processor instance has been shutdown (fail over).
                System.out.println("Caught shutdown exception, skipping checkpoint." + se);
                break;
            } catch (ThrottlingException e) {
                // Backoff and re-attempt checkpoint upon transient failures
                if (i >= (NUM_RETRIES - 1)) {
                    System.err.println("Checkpoint failed after " + (i + 1) + "attempts." + e);
                    break;
                } else {
                    System.out.println("Transient issue when checkpointing - attempt " + (i + 1) + " of " + NUM_RETRIES + e);
                }
            } catch (InvalidStateException e) {
                // This indicates an issue with the DynamoDB table (check for table, provisioned IOPS).
                System.err.println("Cannot save checkpoint to the DynamoDB table used by the Amazon Kinesis Client Library." + e);
                break;
            }
            try {
                Thread.sleep(BACKOFF_TIME_IN_MILLIS);
            } catch (InterruptedException e) {
                System.out.println("Interrupted sleep" + e);
            }
        }
    }

    public void initialize(String s) {
        System.out.println("Initializing record processor");
        this.kinesisShardId = s;


    }

    public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        System.out.println("Processing " + records.size() + " records from " + kinesisShardId);

        // Process records and perform all exception handling.
        processRecordsWithRetries(records);

        // Checkpoint once every checkpoint interval.
        if (System.currentTimeMillis() > nextCheckpointTimeInMillis) {
            checkpoint(checkpointer);
            nextCheckpointTimeInMillis = System.currentTimeMillis() + CHECKPOINT_INTERVAL_MILLIS;
        }
    }

    private void processRecordsWithRetries(List<Record> records) {
        for (Record record : records) {
            boolean processedSuccessfully = false;
            for (int i = 0; i < NUM_RETRIES; i++) {
                try {
                    //
                    // Logic to process record goes here.
                    //
                    processSingleRecord(record);

                    processedSuccessfully = true;
                    break;
                } catch (Throwable t) {
                    System.err.println("Caught throwable while processing record " + record + t);
                }

                // backoff if we encounter an exception.
                try {
                    Thread.sleep(BACKOFF_TIME_IN_MILLIS);
                } catch (InterruptedException e) {
                    System.err.println("Interrupted sleep" + e);
                }
            }

            if (!processedSuccessfully) {
                System.err.println("Couldn't process record " + record + ". Skipping the record.");
            }
        }
    }

    private void processSingleRecord(Record record) {
        // TODO Add your own record processing logic here

        String data = null;
        try {
            // For this app, we interpret the payload as UTF-8 chars.
            data = decoder.decode(record.getData()).toString();

            JSONObject jsonObject = new JSONObject(data);
            JSONObject faceSearchResponses = jsonObject.getJSONArray("FaceSearchResponse").getJSONObject(0);
	    List<Object> matchedFaces = faceSearchResponses.getJSONArray("MatchedFaces").toList();
            if (faceSearchResponses != null) {
                System.out.println("WE FOUND A FACE!!!!!!");
//                System.out.println(data);

		if(matchedFaces.size() > 0){
			System.out.println("We found a face, but it was a friend");
			System.out.println(matchedFaces);
		} else {
                	JSONObject boundingBox = faceSearchResponses.getJSONObject("DetectedFace").getJSONObject("BoundingBox");
                	System.out.println(boundingBox);
                	Double left = Double.parseDouble(String.valueOf(boundingBox.get("Left"))) * 1080;
                	Double right = (left + (Double.parseDouble(String.valueOf(boundingBox.get("Width"))) * 1080));
                	Double middle = ((left + right) / 2);
                	System.out.println("left:\t" + left + "\tright:\t" + right + "\tmiddle:\t" + middle);
                	if (lastFired == 0) {
				// First Firing
                    		lastFired = System.currentTimeMillis();
				Process proc = Runtime.getRuntime().exec("sudo python /home/pi/CleanAndProtec/fire.py");
                	} else {
                    		if ((System.currentTimeMillis() - lastFired) > 5000) {
                   	    		System.out.println("lets fire again");
                        		lastFired = System.currentTimeMillis();
					Process proc = Runtime.getRuntime().exec("sudo python /home/pi/CleanAndProtec/fire.py");
                	    	}
                	}
        		Files.write(Paths.get("/home/pi/CleanAndProtec/middle.txt"), (middle+"").getBytes());
		}
            }

        } catch (NumberFormatException e) {
            System.out.println("Record does not match sample record format. Ignoring record with data; " + data);
        } catch (CharacterCodingException e) {
            System.err.println("Malformed data: " + data + e);
        } catch (Exception e) {
            System.err.println("idk what happened heres the error:\t" + e.toString());
        }
    }

    public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
        System.out.println("Shutting down record processor for shard: " + kinesisShardId);
        // Important to checkpoint after reaching end of shard, so we can start processing data from child shards.
        if (reason == ShutdownReason.TERMINATE) {
            checkpoint(checkpointer);
        }
    }


}
