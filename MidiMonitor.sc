MidiMonitor {
    classvar midiNotes;

    *connectAll {
        MIDIIn.connectAll;
    }

    *monitor { |synthName|
        if (midiNotes == nil, { midiNotes = Array.newClear(128); });
        if (synthName == nil, { "you must name a synth.".throw; });
        
        MIDIdef.noteOn('mmNoteOn', { |vel, note|
            midiNotes[note] = Synth(synthName, ['freq', note.midicps]);
            midiNotes.postln;
        });

        MIDIdef.noteOff('mmNoteOff', { |vel, note|
            midiNotes[note].release;
            midiNotes[note] = nil;
        });
        
    }

    *stopMonitoring {
        MIDIdef.noteOn('mmNoteOn', {});
        MIDIdef.noteOff('mmNoteOff', {});
    }
}