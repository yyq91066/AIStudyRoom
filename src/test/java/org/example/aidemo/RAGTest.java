package org.example.aidemo;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.message.UserMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

@SpringBootTest
public class RAGTest {
    @Test
    public void testReadDocument() {
    //使用FileSystemDocumentLoader读取指定目录下的知识库文档
    //并使用默认的文档解析器TextDocumentParser对文档进行解析
            //https://yyq-oss-blog.oss-cn-beijing.aliyuncs.com/4c60ac34-67f4-49c2-9184-c9b8733c0306.txt
        Document document = FileSystemDocumentLoader.loadDocument("E:/JAVAjava/java/AIDocTest/AIDocumentTest.txt");
        System.out.println(document.text());
    //// 加载单个文档
    //    Document document1 = FileSystemDocumentLoader.loadDocument("E:/knowledge/file.txt", new
    //            TextDocumentParser());
    //// 从一个目录中加载所有文档
    //    List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:/knowledge", new
    //            TextDocumentParser());

    //// 从一个目录及其子目录中加载所有文档
    //    List<Document> documents3 =
    //            FileSystemDocumentLoader.loadDocumentsRecursively("E:/knowledge", new
    //                    TextDocumentParser());
    }

    @Test //PDF解析
    public void PDFAnalysis() {
        //// 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.pdf");
        List<Document> documents2 = FileSystemDocumentLoader.loadDocuments(
                "E:/JAVAjava/java/AIDocTest/yyq",
                pathMatcher,
                new ApachePdfBoxDocumentParser());
        for (Document document : documents2) {
            System.out.println("------------------------------");
            System.out.println(document.text());
        }
    }
    @Test
    public void testReadDocument3() {
        Document document = UrlDocumentLoader.load(
                "https://yyq-oss-blog.oss-cn-beijing.aliyuncs.com/4c60ac34-67f4-49c2-9184-c9b8733c0306.txt",
                new TextDocumentParser());
        System.out.println(document.text());


    }
    @Test
    //计算token长度
    public void testTokenCount() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        Document document = UrlDocumentLoader.load(
                "https://yyq-oss-blog.oss-cn-beijing.aliyuncs.com/4c60ac34-67f4-49c2-9184-c9b8733c0306.txt",
                new TextDocumentParser());
        UserMessage userMessage = UserMessage.userMessage(document.text());

        QwenTokenizer tokenizer = new QwenTokenizer("sk-f99178690ff04631bc18eddbb80fd9e3","qwen-max-latest");
        int count = tokenizer.estimateTokenCountInMessage(userMessage);

        System.out.println("token长度：" + count);

    }

}