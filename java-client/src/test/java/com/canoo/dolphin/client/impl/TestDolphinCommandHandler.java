package com.canoo.dolphin.client.impl;

import com.canoo.dolphin.client.util.AbstractDolphinBasedTest;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * Created by hendrikebbers on 19.02.16.
 */
public class TestDolphinCommandHandler extends AbstractDolphinBasedTest {

    @Test
    public void testInvocation() throws Exception {
        //Given:
        final DolphinTestConfiguration configuration = createDolphinTestConfiguration();
        final ServerDolphin serverDolphin = configuration.getServerDolphin();
        final ClientDolphin clientDolphin = configuration.getClientDolphin();
        final DolphinCommandHandler dolphinCommandHandler = new DolphinCommandHandler(clientDolphin);
        final String modelId = UUID.randomUUID().toString();
        clientDolphin.presentationModel(modelId, new ClientAttribute("myAttribute", "UNKNOWN"));
        serverDolphin.register(new DolphinServerAction() {
            @Override
            public void registerIn(ActionRegistry registry) {
                registry.register("CHANGE_VALUE", new CommandHandler() {
                    @Override
                    public void handleCommand(Command command, List response) {
                        serverDolphin.findPresentationModelById(modelId).getAt("myAttribute").setValue("Hello World");
                    }
                });
            }
        });

        //When:
        dolphinCommandHandler.invokeDolphinCommand("CHANGE_VALUE").get();

        //Then:
        assertEquals(clientDolphin.findPresentationModelById(modelId).getAt("myAttribute").getValue(), "Hello World");
    }

}
