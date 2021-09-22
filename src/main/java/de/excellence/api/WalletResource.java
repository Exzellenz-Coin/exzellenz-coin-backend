package de.excellence.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.excellence.ECBService;
import mainpackage.util.JsonMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.security.PublicKey;

@Path("/api/wallet")
@Produces(MediaType.APPLICATION_JSON)
public class WalletResource {

    @GET
    @Path("balance")
    public BigDecimal getBalance(@QueryParam("walletKey") String walletKey) throws JsonProcessingException {
        PublicKey publicKey = JsonMapper.mapper.readValue('"' + walletKey + '"', PublicKey.class);
        return ECBService.instance.nodeManager.getNode().getBlockChain().getAmount(publicKey);
    }
}
