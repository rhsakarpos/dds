import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.blob.models.BlobItem;
import com.microsoft.azure.storage.blob.models.ContainerCreateResponse;
import com.microsoft.azure.storage.blob.models.ContainerListBlobFlatSegmentResponse;
import com.microsoft.rest.v2.RestException;
import io.reactivex.Single;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;

public class AzureObjectUploader {

    static File createTempFile() throws IOException {

        // Here we are creating a temporary file to use for download and upload to Blob storage
        File sampleFile = null;
        sampleFile = File.createTempFile("sampleFile", ".txt");
        System.out.println(">> Creating a sample file at: " + sampleFile.toString());
        Writer output = new BufferedWriter(new FileWriter(sampleFile));
        output.write("Hello Azure.");
        output.close();

        return sampleFile;
    }

    static void uploadFile(BlockBlobURL blob, File sourceFile) throws IOException {

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(sourceFile.toPath());

        // Uploading a file to the blobURL using the high-level methods available in TransferManager class
        // Alternatively call the PutBlob/PutBlock low-level methods from BlockBlobURL type
        TransferManager.uploadFileToBlockBlob(fileChannel, blob, 8 * 1024 * 1024, null, null)
                .subscribe(response -> {
                    System.out.println("Completed upload request.");
                    System.out.println(response.response().statusCode());
                });
    }

    static void listBlobs(ContainerURL containerURL) {
        // Each ContainerURL.listBlobsFlatSegment call return up to maxResults (maxResults=10 passed into ListBlobOptions below).
        // To list all Blobs, we are creating a helper static method called listAllBlobs,
        // and calling it after the initial listBlobsFlatSegment call
        ListBlobsOptions options = new ListBlobsOptions();
        options.withMaxResults(10);

        containerURL.listBlobsFlatSegment(null, options, null).flatMap(containerListBlobFlatSegmentResponse ->
                listAllBlobs(containerURL, containerListBlobFlatSegmentResponse))
                .subscribe(response -> {
                    System.out.println("Completed list blobs request.");
                    System.out.println(response.statusCode());
                });
    }

    private static Single<ContainerListBlobFlatSegmentResponse> listAllBlobs(ContainerURL url, ContainerListBlobFlatSegmentResponse response) {
        // Process the blobs returned in this result segment (if the segment is empty, blobs() will be null.
        if (response.body().segment() != null) {
            for (BlobItem b : response.body().segment().blobItems()) {
                String output = "Blob name: " + b.name();
                if (b.snapshot() != null) {
                    output += ", Snapshot: " + b.snapshot();
                }
                System.out.println(output);
            }
        } else {
            System.out.println("There are no more blobs to list off.");
        }

        // If there is not another segment, return this response as the final response.
        if (response.body().nextMarker() == null) {
            return Single.just(response);
        } else {
            /*
            IMPORTANT: ListBlobsFlatSegment returns the start of the next segment; you MUST use this to get the next
            segment (after processing the current result segment
            */

            String nextMarker = response.body().nextMarker();

            /*
            The presence of the marker indicates that there are more blobs to list, so we make another call to
            listBlobsFlatSegment and pass the result through this helper function.
            */

            return url.listBlobsFlatSegment(nextMarker, new ListBlobsOptions().withMaxResults(10), null)
                    .flatMap(containersListBlobFlatSegmentResponse ->
                            listAllBlobs(url, containersListBlobFlatSegmentResponse));
        }
    }

    static void deleteBlob(BlockBlobURL blobURL) {
        // Delete the blob
        blobURL.delete(null, null, null)
                .subscribe(
                        response -> System.out.println(">> Blob deleted: " + blobURL),
                        error -> System.out.println(">> An error encountered during deleteBlob: " + error.getMessage()));
    }

    static void getBlob(BlockBlobURL blobURL, File sourceFile) throws IOException {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(sourceFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        TransferManager.downloadBlobToFile(fileChannel, blobURL, null, null)
                .subscribe(response -> {
                    System.out.println("Completed download request.");
                    System.out.println("The blob was downloaded to " + sourceFile.getAbsolutePath());
                });
    }

    public static void main(String[] args) throws java.lang.Exception {
        ContainerURL containerURL;

        // Creating a sample file to use in the sample
        File sampleFile = null;

        try {
            sampleFile = createTempFile();

            File downloadedFile = File.createTempFile("downloadedFile", ".txt");

            // Retrieve the credentials and initialize SharedKeyCredentials
            String accountName = System.getenv("AZURE_STORAGE_ACCOUNT");
            String accountKey = System.getenv("AZURE_STORAGE_ACCESS_KEY");

            // Create a ServiceURL to call the Blob service. We will also use this to construct the ContainerURL
            SharedKeyCredentials creds = new SharedKeyCredentials(accountName, accountKey);
            // We are using a default pipeline here, you can learn more about it at https://github.com/Azure/azure-storage-java/wiki/Azure-Storage-Java-V10-Overview
            final ServiceURL serviceURL = new ServiceURL(new URL("https://" + accountName + ".blob.core.windows.net"), StorageURL.createPipeline(creds, new PipelineOptions()));

            // Let's create a container using a blocking call to Azure Storage
            // If container exists, we'll catch and continue
            containerURL = serviceURL.createContainerURL("quickstart");

            try {
                ContainerCreateResponse response = containerURL.create(null, null, null).blockingGet();
                System.out.println("Container Create Response was " + response.statusCode());
            } catch (RestException e) {
                if (e instanceof RestException && ((RestException) e).response().statusCode() != 409) {
                    throw e;
                } else {
                    System.out.println("quickstart container already exists, resuming...");
                }
            }

            // Create a BlockBlobURL to run operations on Blobs
            final BlockBlobURL blobURL = containerURL.createBlockBlobURL("SampleBlob.txt");


            System.out.println("Uploading the sample file into the container: " + containerURL);
            uploadFile(blobURL, sampleFile);


        } catch (InvalidKeyException e) {
            System.out.println("Invalid Storage account name/key provided");
        } catch (MalformedURLException e) {
            System.out.println("Invalid URI provided");
        } catch (RestException e) {
            System.out.println("Service error returned: " + e.response().statusCode());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
