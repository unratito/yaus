package es.osoco.yaus.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.osoco.yaus.core.YausService;

@WebServlet("/*")
public class YausServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String LOCATION_HEADER = "Location";

    @Inject
    private YausService yausService;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String hash = request.getPathInfo().substring(1);

        String url = yausService.getUrl(hash);

        if (url != null) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader(LOCATION_HEADER, url);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
