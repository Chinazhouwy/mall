package com.mall.common.test;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * RustFS S3 客户端示例
 * 用于演示与 RustFS S3 兼容服务的交互
 */
public class RustfsS3Example {

    // 从配置文件读取的常量
    private static final String RUSTFS_ENDPOINT = "http://rustfs:8081"; // 使用容器网络地址
    private static final String RUSTFS_ACCESS_KEY = "rustfsadmin";
    private static final String RUSTFS_SECRET_KEY = "rustfssecret";
    private static final Region RUSTFS_REGION = Region.US_EAST_1;

    public static void main(String[] args) {
        // 1. 初始化 S3 客户端
        S3Client s3 = createS3Client();

        // 2. 执行基本操作
        performS3Operations(s3);

        // 3. 关闭客户端
        s3.close();
    }

    /**
     * 创建 S3 客户端
     *
     * @return S3Client 实例
     */
    private static S3Client createS3Client() {
        try {
            return S3Client.builder()
                    .endpointOverride(URI.create(RUSTFS_ENDPOINT)) // RustFS 地址
                    .region(RUSTFS_REGION) // RustFS 不验证区域
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(RUSTFS_ACCESS_KEY, RUSTFS_SECRET_KEY)
                            )
                    )
                    .forcePathStyle(true) // 需要与 RustFS 兼容
                    .build();
        } catch (Exception e) {
            System.err.println("创建 S3 客户端失败: " + e.getMessage());
            throw new RuntimeException("S3 客户端初始化失败", e);
        }
    }

    /**
     * 执行 S3 操作
     *
     * @param s3 S3 客户端实例
     */
    private static void performS3Operations(S3Client s3) {
        String bucket = "mall-product-files";

        try {
            // 1. 创建存储桶
            createBucket(s3, bucket);

            // 2. 上传文件
            uploadFile(s3, bucket);

            // 3. 列出对象
            listObjects(s3, bucket);

            // 4. 下载文件
            downloadFile(s3, bucket);

            // 5. 删除对象
            deleteObject(s3, bucket);

        } catch (Exception e) {
            System.err.println("执行 S3 操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建存储桶
     *
     * @param s3 S3 客户端实例
     * @param bucket 存储桶名称
     */
    private static void createBucket(S3Client s3, String bucket) {
        try {
            s3.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
            System.out.println("存储桶创建成功: " + bucket);
        } catch (BucketAlreadyExistsException | BucketAlreadyOwnedByYouException e) {
            System.out.println("存储桶已存在: " + bucket);
        } catch (Exception e) {
            System.err.println("创建存储桶失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param s3 S3 客户端实例
     * @param bucket 存储桶名称
     */
    private static void uploadFile(S3Client s3, String bucket) {
        try {
            // 创建一个示例文件
            Path tempFile = createTempFile();

            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key("hello.txt")
                            .build(),
                    tempFile
            );
            System.out.println("文件上传成功: hello.txt");
        } catch (Exception e) {
            System.err.println("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 创建临时文件用于测试
     *
     * @return 临时文件路径
     */
    private static Path createTempFile() throws IOException {
        Path tempFile = Paths.get("hello.txt");
        String content = "Hello, RustFS!";
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }

    /**
     * 列出存储桶中的对象
     *
     * @param s3 S3 客户端实例
     * @param bucket 存储桶名称
     */
    private static void listObjects(S3Client s3, String bucket) {
        try {
            ListObjectsV2Response listResponse = s3.listObjectsV2(
                    ListObjectsV2Request.builder().bucket(bucket).build()
            );
            System.out.println("存储桶中的对象:");
            listResponse.contents().forEach(obj ->
                    System.out.println("  - " + obj.key() + " (大小: " + obj.size() + " bytes)")
            );
        } catch (Exception e) {
            System.err.println("列出对象失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param s3 S3 客户端实例
     * @param bucket 存储桶名称
     */
    private static void downloadFile(S3Client s3, String bucket) {
        try {
            s3.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucket)
                            .key("hello.txt")
                            .build(),
                    Paths.get("downloaded-hello.txt")
            );
            System.out.println("文件下载成功: downloaded-hello.txt");
        } catch (Exception e) {
            System.err.println("文件下载失败: " + e.getMessage());
        }
    }

    /**
     * 删除对象
     *
     * @param s3 S3 客户端实例
     * @param bucket 存储桶名称
     */
    private static void deleteObject(S3Client s3, String bucket) {
        try {
            s3.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucket)
                            .key("hello.txt")
                            .build()
            );
            System.out.println("文件删除成功: hello.txt");
        } catch (Exception e) {
            System.err.println("删除文件失败: " + e.getMessage());
        }
    }
}
