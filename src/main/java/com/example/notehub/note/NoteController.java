package com.example.notehub.note;

import com.example.notehub.dto.PagedResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping(path = "/search")
    public PagedResult<Note> searchNotes(@RequestParam("q") String query, @RequestParam(value = "sids", required = false) List<Long> subjectIds, @RequestParam(value = "page",defaultValue = "0") Long page){
        return noteService.searchNotes(query,subjectIds,page);
    }

    @GetMapping(path = "/me")
    public PagedResult<Note> getMyNotes(@RequestParam(defaultValue = "0") Long page){
        return noteService.getMyNotes(page);
    }

    @DeleteMapping(path = "/{noteId}")
    public void deleteNote(@PathVariable Long noteId){
        noteService.deleteNote(noteId);
    }
}
