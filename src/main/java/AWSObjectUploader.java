import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;

public class AWSObjectUploader {

    private static S3Client s3;

    public static void main(String[] args) {

    }

    public static void uploadObjectToBucket(String bucket, String content) {
        Region region = Region.US_EAST_2;
        s3 = S3Client.builder().region(region).build();


        String key = String.valueOf(System.currentTimeMillis());

        byte[] b = content.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(b);

        // Put Object
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                        .build(),
                RequestBody.fromByteBuffer(byteBuffer));
    }
}
