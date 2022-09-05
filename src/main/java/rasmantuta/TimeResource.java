package rasmantuta;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;
import java.util.Optional;

@Path("/time")
public class TimeResource {


    @ConfigProperty(name = "default.timezone")
    String defaultTimeZone;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("timeZone") Optional<String> timeZone) {
        String retval = "";
        ZoneId zoneId;
        String zoneName = timeZone.orElse(defaultTimeZone);
        try {
            zoneId = ZoneId.of(zoneName);
        } catch (DateTimeException e) {
            zoneId = ZoneId.of("UTC");
            retval = "Unknown timezone [" + zoneName + "], using UTC\n ";
        }
        retval += ZonedDateTime.now(zoneId).toString();
        return retval;
    }
}