import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
        AmazonS3 s3Client = AmazonS3Client.builder().withRegion(" eu-west-1").build();
        S3Object s3Object = s3Client.getObject("awstextracts3example","textractss3.jpg");
            S3ObjectInputStream s3ObjectInputStream =s3Object.getObjectContent();

            SdkBytes bytes = SdkBytes.fromInputStream(s3ObjectInputStream);
            Document doc = Document.builder().bytes(bytes).build();

            List<FeatureType> list = new ArrayList<>();
            list.add(FeatureType.FORMS);

            AnalyzeDocumentRequest request = AnalyzeDocumentRequest.builder().featureTypes(list).document(doc).build();

            TextractClient textractClient = TextractClient.builder().region(Region.EU_WEST_1).build();

            AnalyzeDocumentResponse response = textractClient.analyzeDocument(request);
            List<Block> blocks = response.blocks();

            for(Block  b : blocks){
                System.out.println(b.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
