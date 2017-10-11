package com.canoo.platform.client.http;

import java.io.IOException;

public interface HttpRequest {

    HttpResponse withContent(byte[] content) throws IOException;

    HttpResponse withContent(byte[] content, String contentType) throws IOException;

    HttpResponse withContent(String content) throws IOException;

    HttpResponse withContent(String content, String contentType) throws IOException;

    <I> HttpResponse withContent(I content) throws IOException;

    HttpResponse withoutContent() throws IOException;

}
