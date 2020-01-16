package by.khomenko.nsp.webcat.client.servlet.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BaseCommand {

    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
