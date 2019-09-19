/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.discordsrv.common.api;

import com.discordsrv.common.api.event.Event;
import com.discordsrv.common.logging.Log;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventBus {

    @Getter private Set<Object> listeners = new HashSet<>();

    /**
     * Subscribe the given instance to DiscordSRV events
     * @param listener the instance to subscribe DiscordSRV events to
     * @throws RuntimeException if the object has zero methods that are annotated with {@link Subscribe}
     */
    public void subscribe(Object listener) {
        // ensure at least one method available in given object that is annotated with Subscribe
        int methodsAnnotatedSubscribe = 0;
        for (Method method : listener.getClass().getMethods()) if (method.isAnnotationPresent(Subscribe.class)) methodsAnnotatedSubscribe++;
        if (methodsAnnotatedSubscribe == 0) throw new RuntimeException(listener.getClass().getName() + " attempted DiscordSRV API registration but no methods inside of it were annotated @Subscribe (github.scarsz.discordsrv.api.Subscribe)");

        listeners.add(listener);
    }

    /**
     * Unsubscribe the given instance from DiscordSRV events
     * @param listener the instance to unsubscribe DiscordSRV events from
     * @return whether or not the instance was a listener
     */
    public boolean unsubscribe(Object listener) {
        return listeners.remove(listener);
    }

    public Object getListener(Class listenerClass) {
        Set<Object> found = getListeners(o -> o.getClass() == listenerClass);
        return found.size() != 0 ? found.iterator().next() : null;
    }

    public Set<Object> getListeners(Predicate<Object> filter) {
        return listeners.stream().filter(filter).collect(Collectors.toSet());
    }

    /**
     * Propagate the given event to all listeners
     * @param event the event to be called
     * @return the event that was called
     */
    public <E extends Event> E publish(E event) {
        for (ListenerPriority priority : ListenerPriority.values()) {
            for (Object listener : listeners) {
                for (Method method : listener.getClass().getMethods()) {
                    if (method.getParameters().length > 1) continue; // api listener methods always take at least one parameter
                    // TODO this probably fails now that events are interfaces instead of classes
                    if (!method.getParameters()[0].getType().isAssignableFrom(event.getClass())) continue; // make sure the event wants this event
                    if (!method.isAnnotationPresent(Subscribe.class)) continue; // make sure method has a subscribe annotation somewhere

                    for (Annotation annotation : method.getAnnotations()) {
                        if (!(annotation instanceof Subscribe)) continue; // go through all the annotations until we get one of ours

                        Subscribe subscribeAnnotation = (Subscribe) annotation;
                        if (subscribeAnnotation.priority() != priority) continue; // this priority isn't being called right now

                        // make sure method is accessible
                        if (!method.isAccessible()) method.setAccessible(true);

                        List<Object> arguments = new LinkedList<>();
                        arguments.add(event);
                        for (Class<?> clazz : ArrayUtils.subarray(method.getParameterTypes(), 1, method.getParameterCount() + 1)) {
                            String thing = clazz.getSimpleName();
                            for (Method m : event.getClass().getMethods()) {
                                if (m.getName().equalsIgnoreCase("is" + thing) ||
                                        m.getName().equalsIgnoreCase("get" + thing)) {
                                    m.setAccessible(true);
                                    try {
                                        arguments.add(method.invoke(event));
                                        continue;
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        Log.debug(
                                                String.format("Failed to get parameter value for %s#%s: %s",
                                                        listener.getClass().getName(),
                                                        method.getName(),
                                                        e.getMessage()
                                                )
                                        );
                                    }
                                }

                                arguments.add(null);
                            }
                        }

                        try {
                            method.invoke(listener, arguments.toArray());
                        } catch (InvocationTargetException e) {
                            Log.error(
                                    String.format("Listener %s#%s threw an exception while processing " + event.getClass().getSimpleName() + ":\n%s",
                                            listener.getClass().getName(),
                                            method.toString(),
                                            ExceptionUtils.getStackTrace(e)
                                    )
                            );
                        } catch (IllegalAccessException e) {
                            // this should never happen
                            Log.error(
                                    String.format("Listener %s#%s wasn't accessible:\n%s",
                                            listener.getClass().getName(),
                                            method.toString(),
                                            ExceptionUtils.getStackTrace(e)
                                    )
                            );
                        }
                    }
                }
            }
        }

        return event;
    }

}
