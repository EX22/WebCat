package by.khomenko.nsp.webcat.servlet.command;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

public interface BaseCommand {

    void execute(HttpRequest request, HttpResponse response);

}
