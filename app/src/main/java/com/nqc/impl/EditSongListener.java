package com.nqc.impl;

import com.nqc.model.Song;

public interface EditSongListener {
    void saveEditSong(Song song, String email);
}
