package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.common.abstracted.channel.BaseChannelManager;
import com.discordsrv.common.logging.Log;

import java.util.*;

public class ChannelManagerImpl extends BaseChannelManager {
    @Override
    public void load() {
        Map<String, Object> channelsFromConfig = DiscordSRV.get().getConfig().getMap("Channels");
        getChannels().clear();
        Log.debug("Loading channels from " + channelsFromConfig);
        for (Map.Entry<String, Object> entry : channelsFromConfig.entrySet()) {
            Set<String> channelsToLink = new HashSet<>();
            if (entry.getValue() instanceof Collection) {
                //noinspection unchecked
                ((Collection) entry.getValue()).stream()
                        .map(String::valueOf)
                        .forEach(o -> channelsToLink.add((String) o));
            } else {
                channelsToLink.add(String.valueOf(entry.getValue()));
            }

            Log.debug("Channel " + entry.getKey() + " <-> " + channelsToLink);
            //TODO plugin channel implementations
            VanillaChannel channel = new VanillaChannel(channelsToLink);
            getChannels().add(channel);
        }
    }

    @Override
    public Optional<BaseChannel> getChannel(String target) {
        return super.getChannel(target);
    }
}
