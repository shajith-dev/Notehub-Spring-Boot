package com.example.notehub.note;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Note createNote(@RequestPart("note") String noteJson,@RequestPart("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Note note = mapper.readValue(noteJson,Note.class);
        return noteService.createNote(note,file);
    }

    @PutMapping(path = "/noteId")
    public void toggleLike(@PathVariable("noteId") Long noteId,@RequestParam String like){
        noteService.toggleLike(noteId,like);
    }

}
