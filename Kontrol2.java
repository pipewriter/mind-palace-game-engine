import javax.sound.midi.*;
import java.util.Queue;
import java.util.LinkedList;


enum ButtonMode {
    Toggle, Momentary
}

class Scene {
    byte[] data;
    Group[] groups;
    TransportButton prevTrack;
    TransportButton nextTrack;
    TransportButton setMarker;
    TransportButton prevMarker;
    TransportButton nextMarker;
    TransportButton cycle;
    TransportButton rewind;
    TransportButton fastForward;
    TransportButton stop;
    TransportButton play;
    TransportButton record;

    Scene() {
        groups = new Group[8];
        for (int i = 0; i < 8; i++) {
            groups[i] = new Group(i + 1);
        }

        prevTrack = new TransportButton(0, "PrevTrack");
        nextTrack = new TransportButton(1, "NextTrack");
        setMarker = new TransportButton(3, "Marker Set");
        prevMarker = new TransportButton(4, "PrevMarker");
        nextMarker = new TransportButton(5, "NextMarker");
        cycle = new TransportButton(2, "Cycle");
        rewind = new TransportButton(6, "Rewind");
        fastForward = new TransportButton(7, "FastForward");
        stop = new TransportButton(8, "Stop");
        play = new TransportButton(9, "Play");
        record = new TransportButton(10, "Record");
    }

    // This should only be called once the Kontrol2 gets a scene-dump response.
    void setData(byte[] data) {
        this.data = data;
    }

    void display() {
        System.out.println("Global midi channel: " + globalMidiChannel());
        System.out.println("Control Mode: " + controlMode());
        System.out.println("LED Mode: " + ledMode());

        for (int groupNum = 1; groupNum <= 8; groupNum++) {
            System.out.println();
            group(groupNum).display();
        }

        System.out.println();
        System.out.println("Transport button MIDI channel: " + transportButtonMidiChannel());
        prevTrack().display();
        nextTrack().display();
        setMarker().display();
        prevMarker().display();
        nextMarker().display();
        cycle().display();
        rewind().display();
        fastForward().display();
        stop().display();
        play().display();
        record().display();
    }

    int globalMidiChannel() {
        return (int) p(0);
    }

    String controlMode() {
        return pick(p(1), "CC Mode", "Cubase", "DP", "Live", "ProTools", "SONAR");
    }

    String ledMode() {
        return pick(p(2), "Internal", "External");
    }

    int channelMidiChannel() {
        return p(0);
    }

    Group group(int groupNum) {
        return groups[groupNum - 1];
    }

    class Group {
        // groupNum is 1-based: 1..8
        int groupNum;
        GradualControl slider;
        GradualControl knob;
        Button solo;
        Button mute;
        Button record;

        Group(int groupNum) {
            this.groupNum = groupNum;
            slider = new GradualControl(1, "Slider");
            knob = new GradualControl(7, "Knob");
            solo = new Button(13, "Solo");
            mute = new Button(19, "Mute");
            record = new Button(25, "Record");
        }

        void display() {
            System.out.println("Group " + groupNum);
            System.out.println("Group midi channel: " + midiChannel());
            slider().display();
            knob().display();
            soloButton().display();
            muteButton().display();
            recordButton().display();
        }

        int midiChannel() {
            return groupP(0);
        }

        GradualControl slider() {
            return slider;
        }

        GradualControl knob() {
            return knob;
        }

        Button soloButton() {
            return solo;
        }

        Button muteButton() {
            return mute;
        }

        Button recordButton() {
            return record;
        }

        class GradualControl {
            int paramOffsetInGroup;
            String label;

            GradualControl(int pOff, String label) {
                paramOffsetInGroup = pOff;
                this.label = label;
            }

            void display() {
                System.out.println(label + " assign type: " + assignType());
                System.out.println(label + " note number: " + noteNumber());
                System.out.println(label + " range: " + minValue() + ".." + maxValue());
            }

            String assignType() {
                return pick(groupP(paramOffsetInGroup + 0), "Disabled", "Enabled");
            }

            int noteNumber() {
                return groupP(paramOffsetInGroup + 2);
            }

            int minValue() {
                return groupP(paramOffsetInGroup + 3);
            }

            int maxValue() {
                return groupP(paramOffsetInGroup + 4);
            }
        }

        class Button {
            int paramOffsetInGroup;
            String label;

            Button(int pOff, String label) {
                paramOffsetInGroup = pOff;
                this.label = label;
            }

            void display() {
                System.out.println(label + " Button assign type    : " + assignType());
                System.out.println(label + " Button behavior       : " + behavior());
                System.out.println(label + " Button note number    : " + noteNumber());
                System.out.println(label + " Button trigger values : " + offValue() + ".." + onValue());
            }

            String assignType() {
                return pick(groupP(paramOffsetInGroup + 0), "No Assign", "CC", "Note");
            }

            String behavior() {
                return pick(groupP(paramOffsetInGroup + 1), "Momentary", "Toggle");
            }

            int noteNumber() {
                return groupP(paramOffsetInGroup + 2);
            }

            int offValue() {
                return groupP(paramOffsetInGroup + 3);
            }

            int onValue() {
                return groupP(paramOffsetInGroup + 4);
            }

            SceneChange setBehavior(ButtonMode newMode) {
                boolean momentary = newMode == ButtonMode.Momentary;
                byte newByteVal = (byte) (momentary ? 0x00 : 0x01);
                return writeToScene(groupPNum(paramOffsetInGroup + 1), newByteVal);
            }
        }

        private byte groupP(int groupParamNum) {
            return p(groupPNum(groupParamNum));
        }

        private int groupPNum(int groupParamNum) {
            // groupNum = 1-based: 1..8
            int groupIndex = groupNum - 1;
            int getPastFirstThreeVals = 3;
            int numParamsPerGroup = 31;
            int paramNum = getPastFirstThreeVals + (groupIndex * numParamsPerGroup) + groupParamNum;
            return paramNum;
        }
    }

    int transportButtonMidiChannel() {
        return p(251);
    }

    TransportButton prevTrack() {
        return prevTrack;
    }

    TransportButton nextTrack() {
        return nextTrack;
    }

    TransportButton setMarker() {
        return setMarker;
    }

    TransportButton prevMarker() {
        return prevMarker;
    }

    TransportButton nextMarker() {
        return nextMarker;
    }

    TransportButton cycle() {
        return cycle;
    }

    TransportButton rewind() {
        return rewind;
    }

    TransportButton fastForward() {
        return fastForward;
    }

    TransportButton stop() {
        return stop;
    }

    TransportButton play() {
        return play;
    }

    TransportButton record() {
        return record;
    }

    class TransportButton {
        int paramOffset;
        String label;

        TransportButton(int paramOffset, String label) {
            this.paramOffset = 252 + (paramOffset * 6);
            this.label = label;
        }

        void display() {
            System.out.println(label + " assign type:     " + assignType());
            System.out.println(label + " button behavior: " + buttonBehavior());
            System.out.println(label + " note number:     " + noteNumber());
            System.out.println(label + " trigger values:  " + offValue() + ".." + onValue());
        }

        SceneChange setBehavior(ButtonMode newMode) {
            boolean momentary = newMode == ButtonMode.Momentary;
            byte newByteVal = (byte) (momentary ? 0x00 : 0x01);
            // System.out.println("Setting " + label + " mode to " + newMode + ", param
            // offset: " + (paramOffset + 1));
            return writeToScene(paramOffset + 1, newByteVal);
        }

        String assignType() {
            return pick(p(paramOffset + 0), "No Assign", "CC", "Note");
        }

        String buttonBehavior() {
            return pick(p(paramOffset + 1), "Momentary", "Toggle");
        }

        int noteNumber() {
            return p(paramOffset + 2);
        }

        int offValue() {
            return p(paramOffset + 3);
        }

        int onValue() {
            return p(paramOffset + 4);
        }
    }

    // 'p' = short for 'parameter'
    private byte p(int paramNumber) {
        return data[paramNumberToByteIndex(paramNumber)];
    }

    /*
     * private void setP(int paramNumber, byte newVal) {
     * System.out.println("changing " + paramNumber + " from " +
     * data[paramNumberToByteIndex(paramNumber)] + " to " + newVal);
     * 
     * data[paramNumberToByteIndex(paramNumber)] = newVal;
     * System.out.println(data[paramNumberToByteIndex(paramNumber)]); }
     */

    private SceneChange writeToScene(int paramNumber, byte newValue) {
        // System.out.println("gonna request a change... " + paramNumber + " from " +
        // data[paramNumberToByteIndex(paramNumber)] +
        // " to " + newValue);

        int byteArrayIndex = paramNumberToByteIndex(paramNumber);
        return new SceneChange(byteArrayIndex, newValue);
    }

    private int paramNumberToByteIndex(int paramNumber) {
        int indexBump = paramNumber / 7; // int division =~ floor
        int headerLength = 13;
        int index = headerLength + paramNumber + indexBump + 1;
        return index;
    }

    private String pick(int index, String... choices) {
        return choices[index];
    }
}

class SceneChange {
    int byteArrayIndex;
    byte newValue;

    SceneChange(int byteArrayIndex, byte newValue) {
        this.byteArrayIndex = byteArrayIndex;
        this.newValue = newValue;
    }
}

public class Kontrol2 implements Receiver {
    private MidiDevice inDevice;
    private MidiDevice outDevice;
    private Scene scene;
    private Queue<SceneChange> sceneChanges;
    private boolean debug;

    // public
    Kontrol2() {
        this(false);
    }

    public boolean isPluggedIn(){
        return outDevice != null;
    }

    // not public since it cant do it
    // public 
    Kontrol2(boolean debug) {
        this.debug = debug;

        debug("----------------------------------");
        debug(" Hi!");
        debug(" Since you're debugging, you might want to know how to factory-reset your Korg nanoKontrol2:");
        debug(" 1. Unplug it from USB");
        debug(" 2. Hold down these buttons: Previous Track, Next Track, Cycle");
        debug(" 3. Plug in the USB");
        debug(" Once the 'Rewind', 'Fast Forward', 'Stop', 'Play', and 'Record' buttons stop blinking, let go.");
        debug(" You're all set.");
        debug("----------------------------------");

        this.inDevice = getNanoKontrol2InputDevice();
        this.outDevice = getNanoKontrol2OutputDevice();
        if(this.outDevice == null){
            return;
        }
        this.scene = new Scene();
        this.sceneChanges = new LinkedList<SceneChange>();
        requestSceneData();
    }

    private void requestSceneData() {
        debug("Requesting scene data...");
        sendMessageToDevice(new byte[] { (byte) 0xf0, // exclusive status
                (byte) 0x42, // ...for Korg
                (byte) 0x40, // 4g, where g = the Global midi channel. I'm assuming it's 0 here, but that
                             // could change.
                (byte) 0x00, // "Software Project (nanoKONTROL2: 000113H)". IDK
                (byte) 0x01, // no info
                (byte) 0x13, // no info
                (byte) 0x00, // "Sub ID",
                (byte) 0x1f, // 00 = Native mode I/O Request, 1f = Data dump request, 7f = data dump
                (byte) 0x10, // 10 = current scene data dump request (if prev byte == 1f)
                (byte) 0x00, // sort of an empty placeholder for the data
                (byte) 0xf7 // EOX ("End of Exclusive")
        });
    }

    private void sendMessageToDevice(byte[] messageBytes) {
        if(outDevice == null){
            System.out.println("KORG NOT PLUGGED IN");
            System.exit(1);
        }
        debug("Sending message: ...");
        try {
            SysexMessage message = new SysexMessage(messageBytes, messageBytes.length);

            // Always send 0 for the current timestamp. According to themidibus' README,
            // passing the timestamps breaks MMJ, and passing 0 doesn't seem to affect
            // it on Linux, so, just do that. If we find a reason to send actual
            // timestamps, we'll reconsider.
            outDevice.getReceiver().send(message, 0);
            // outDevice.getReceiver().send(message, System.currentTimeMillis());

        } catch (InvalidMidiDataException x) { // if you borked the message
            System.out.println(x);
        } catch (MidiUnavailableException x) { // sheesh
            System.out.println(x);
        }
    }

    int[] sliders = new int[8];
    int[] dials = new int[8];
    private boolean[] soloButtons = new boolean[8];
    private boolean[] muteButtons = new boolean[8];
    private boolean[] recordButtons = new boolean[8];
    private boolean prevTrack;
    private boolean nextTrack;
    private boolean setMarker;
    private boolean prevMarker;
    private boolean nextMarker;
    private boolean cycle;
    private boolean rewind;
    private boolean fastForward;
    private boolean stop;
    private boolean play;
    private boolean record;

    public int slider(int index) {
        return sliders[index - 1];
    }

    public int dial(int index) {
        return dials[index - 1];
    }

    public boolean solo(int index) {
        return soloButtons[index - 1];
    }

    public boolean mute(int index) {
        return muteButtons[index - 1];
    }

    public boolean record(int index) {
        return recordButtons[index - 1];
    }

    public boolean prevTrack() {
        return this.prevTrack;
    }

    public boolean nextTrack() {
        return this.nextTrack;
    }

    public boolean setMarker() {
        return this.setMarker;
    }

    public boolean prevMarker() {
        return this.prevMarker;
    }

    public boolean nextMarker() {
        return this.nextMarker;
    }

    public boolean cycle() {
        return this.cycle;
    }

    public boolean rewind() {
        return this.rewind;
    }

    public boolean fastForward() {
        return this.fastForward;
    }

    public boolean stop() {
        return this.stop;
    }

    public boolean play() {
        return this.play;
    }

    public boolean record() {
        return this.record;
    }

    public void buttonMode(ButtonMode buttonMode) {
        soloMode(buttonMode);
        muteMode(buttonMode);
        recordMode(buttonMode);
        transportButtonsMode(buttonMode);
    }

    public void buttonMode(int index, ButtonMode buttonMode) {
        soloMode(index, buttonMode);
        muteMode(index, buttonMode);
        recordMode(index, buttonMode);
    }

    public void soloMode(ButtonMode buttonMode) {
        for (int i = 1; i <= 8; i++) {
            soloMode(i, buttonMode);
        }
    }

    public void muteMode(ButtonMode buttonMode) {
        for (int i = 1; i <= 8; i++) {
            muteMode(i, buttonMode);
        }
    }

    public void recordMode(ButtonMode buttonMode) {
        for (int i = 1; i <= 8; i++) {
            recordMode(i, buttonMode);
        }
    }

    public void soloMode(int index, ButtonMode buttonMode) {
        queueSceneChange(scene.group(index).soloButton().setBehavior(buttonMode));
    }

    public void muteMode(int index, ButtonMode buttonMode) {
        queueSceneChange(scene.group(index).muteButton().setBehavior(buttonMode));
    }

    public void recordMode(int index, ButtonMode buttonMode) {
        queueSceneChange(scene.group(index).recordButton().setBehavior(buttonMode));
    }

    public void transportButtonsMode(ButtonMode md) {
        cycleButtonMode(md);
        rewindButtonMode(md);
        fastForwardButtonMode(md);
        stopButtonMode(md);
        playButtonMode(md);
        recordButtonMode(md);
    }

    public void cycleButtonMode(ButtonMode md) {
        queueSceneChange(scene.cycle().setBehavior(md));
    }

    public void rewindButtonMode(ButtonMode md) {
        queueSceneChange(scene.rewind().setBehavior(md));
    }

    public void fastForwardButtonMode(ButtonMode md) {
        queueSceneChange(scene.fastForward().setBehavior(md));
    }

    public void stopButtonMode(ButtonMode md) {
        queueSceneChange(scene.stop().setBehavior(md));
    }

    public void playButtonMode(ButtonMode md) {
        queueSceneChange(scene.play().setBehavior(md));
    }

    public void recordButtonMode(ButtonMode md) {
        queueSceneChange(scene.record().setBehavior(md));
    }

    // queuing scene writes
    void queueSceneChange(SceneChange sc) {
        debug("Queueing SceneChange. (" + sceneChanges.size() + " change queued.) "
                + (scene.data == null ? "(No scene data)" : ""));
        sceneChanges.add(sc);
        if (scene.data != null && sceneChanges.size() == 1) {
            scheduleScenePump();
        }
    }

    void scheduleScenePump() {
        debug("Scheduling Scene Pump in 25ms");
        new ScenePumper(25).run();
    }

    class ScenePumper extends Thread {
        private final int msDelay;

        ScenePumper(int msDelay) {
            this.msDelay = msDelay;
        }

        public void run() {
            try {
                sleep(msDelay);
            } catch (InterruptedException x) {
            }
            pumpSceneChanges();
        }

        private void pumpSceneChanges() {
            // Get the current size first, in case new messages come in in the meantime.
            while (!sceneChanges.isEmpty()) {
                SceneChange sc = sceneChanges.remove();
                debug("Applying SceneChange...");
                scene.data[sc.byteArrayIndex] = sc.newValue;
            }
            debug("Sending SceneChange request.");
            sendMessageToDevice(scene.data);
        }
    }

    // Receiver methods:
    public void send(MidiMessage midiMessage, long timestamp) { // send, aka receive
        debug("WE GOT ONE! " + midiMessage.getStatus() + " " + ShortMessage.CONTROL_CHANGE);
        if (midiMessage.getStatus() == ShortMessage.CONTROL_CHANGE) { // nanoKONTROL only sends CONTROL messages. (Well.
                                                                      // Sysex messages, too.)
            ShortMessage message = (ShortMessage) midiMessage;

            int channelId = message.getData1();
            int messageValue = message.getData2();

            debug(channelId + ": " + messageValue);

            if (0 <= channelId && channelId <= 7) {
                sliders[channelId] = messageValue;
            } else if (16 <= channelId && channelId <= 24) {
                dials[channelId - 16] = messageValue;
            } else if (32 <= channelId && channelId <= 39) {
                soloButtons[channelId - 32] = messageValue == 127;
            } else if (48 <= channelId && channelId <= 55) {
                muteButtons[channelId - 48] = messageValue == 127;
            } else if (64 <= channelId && channelId <= 71) {
                recordButtons[channelId - 64] = messageValue == 127;
            } else if (channelId == 58) {
                prevTrack = messageValue == 127;
            } else if (channelId == 59) {
                nextTrack = messageValue == 127;
            } else if (channelId == 60) {
                setMarker = messageValue == 127;
            } else if (channelId == 61) {
                prevMarker = messageValue == 127;
            } else if (channelId == 62) {
                nextMarker = messageValue == 127;
            } else if (channelId == 46) {
                cycle = messageValue == 127;
            } else if (channelId == 43) {
                rewind = messageValue == 127;
            } else if (channelId == 44) {
                fastForward = messageValue == 127;
            } else if (channelId == 42) {
                stop = messageValue == 127;
            } else if (channelId == 41) {
                play = messageValue == 127;
            } else if (channelId == 45) {
                record = messageValue == 127;
            }
        } else {
            byte[] data = midiMessage.getMessage();
            debug("Got a message..." + data.length + " bytes long");

            if (midiMessage.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
                debug("Got a scene dump!");
                if (data[7] == 0x7f && data[12] == 0x40) {
                    scene.setData(data);
                    scheduleScenePump();
                    // if (debug) { scene.display(); }
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public void close() {
        if(inDevice != null)
            this.inDevice.close();
        if(outDevice!= null)
            this.outDevice.close();
    }

    // initialization methods
    private MidiDevice getNanoKontrol2InputDevice() {
        return getNanoKontrol2Device(true);
    }

    private MidiDevice getNanoKontrol2OutputDevice() {
        return getNanoKontrol2Device(false);
    }

    private MidiDevice getNanoKontrol2Device(boolean forInput) {
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            if (info.getName().indexOf("nanoKONTROL2") >= 0 || info.getDescription().indexOf("nanoKONTROL2") >= 0) {
                MidiDevice device = null;
                debug("Trying to open MIDI device (for " + (forInput ? "input" : "output") + "): " + info.getName()
                        + " :: " + info.getDescription());
                try {
                    device = MidiSystem.getMidiDevice(info);
                    if (!device.isOpen()) {
                        debug("  opening the device...");
                        device.open();
                    }

                    if (forInput && device.getMaxTransmitters() != 0) { // -1 means unlimited. only == 0 is bad.
                        debug("  setting THIS as the receiver...");
                        device.getTransmitter().setReceiver(this); // Wire us up to this device, so our #send() method
                                                                   // is called.
                        return device; // Return it only so we can close() it when we're done.
                    } else if (!forInput && device.getMaxReceivers() != 0) { // -1 means unlimited. only == 0 is bad.
                        debug("  using this device for output!");
                        // Nothing to do; we'll send messages to it ourselves.
                        return device; // Return it so we can send messages to it, and close() it when we're done.
                    }
                } catch (MidiUnavailableException x) {
                    System.err.println("Tried to connect to this Midi device: " + device
                            + " but it looks like Midi is unavailable");
                    x.printStackTrace();
                    return null;
                }
            }
        }
        // System.err.println("Couldn't find the korg nanoKontrol2!");
        return null;
    }

    private void debug(String message) {
        if (this.debug) {
            System.out.println(message);
        }
    }
}

// class Test {

//     public static void main(String[] args) throws InterruptedException {
//         Kontrol2 k2 = new Kontrol2();
//         System.out.println(k2.);
//         while (true) {
//             Thread.sleep(100);
//             for (int i = 1; i <= 8; i++) {
//                 System.out.print(String.format("%03d-", k2.dial(i)));
//             }
//             System.out.println();
//             for (int i = 1; i <= 8; i++) {
//                 System.out.print(String.format("%03d-", k2.slider(i)));
//             }
//             System.out.println();
//             System.out.println();
//         }
//     }
// }
