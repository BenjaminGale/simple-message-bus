Messenger
=========

Provides loosely coupled communication for applications.

The primary interface is the `Messenger` which provides access to `MessageService` objects which allow you to subscribe to or publish a particular message.

Components of your application can communicate via the `Messenger` meaning the components do not have to know about each other which reduces coupling.

Messages
--------

A message is typically represented by a POJO which has properties that provide access to the message state. Messages do not have to derive from any special base classes or implement any interfaces.

Messages should provide data only (i.e. no behaviour).

An example message might look like this:

```
public class SillyMessage {

    public boolean isCondition() {
        return true;
    }
    
    public int getCount() {
        return 42;
    }
    
    public String getText() {
        return "XYZ";
    }
}
```

MessageService objects
--------------------

A `MessageService` object allows you to subscribe to or publish a message of a specific type. A `MessageService` can be obtained using the `getMessage` method of the `Messenger` like so:

```
Messenger messenger = new DefaultMessenger();
MessageService<SillyMessage> service = messenger.getMessage(SillyMessage.class);
```
Once a `MessageService` object is retrieved, you can subscribe to and publish messages like so:

```
// Subscribe to a message.
service.subscribe(m -> { /* do something with the message here... */ });

// Publish a message.
service.publish(new SillyMessage());
```

Once a message is published, all registered subscribers will be notified and be given a chance to react. 

Please note the following:

 - The order in which the event handlers are invoked is undefined. i.e. don't rely on them being invoked in any particular order.
 - A message can still be published even if there are no subscribers. In this case, nothing happens.
 
Note that the above examples shows a longhand way of publishing and subscribing to messages. Once an instance to a `Messenger` is obtained the typical usage is as follows:

```
// Subscibing to a message.
messenger.getMessage(SillyMessage.class).subscribe(m -> { });

// Publishing a message.
messenger.getMessage(SillyMessage.class).publish(new SillyMessage());

```
