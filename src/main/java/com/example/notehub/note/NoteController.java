package com.example.notehub.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping(path = "/{noteId}")
    public Note getNote(@PathVariable("noteId") Long noteId){
        return noteService.getNote(noteId);
    }

    @PostMapping
    public Note createNote(@RequestParam("note") Note note,@RequestParam("file") MultipartFile file){
        return noteService.createNote(note,file);
    }

    @PutMapping(path = "/noteId")
    public void toggleLike(@PathVariable("noteId") Long noteId,@RequestParam String like){
        noteService.toggleLike(noteId,like);
    }

}
