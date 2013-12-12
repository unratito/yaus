package es.osoco.yaus.web;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import es.osoco.yaus.core.YausService;

@Model
public class UrlShortener {
    @Inject
    private YausService yausService;

    private String url;
    private String shortUrl;

    public void shorten() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        StringBuffer buffer = request.getRequestURL();

        String context = buffer.substring(0, buffer.lastIndexOf("/") + 1);

        String hash = yausService.addUrl(url);

        shortUrl = context + hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
