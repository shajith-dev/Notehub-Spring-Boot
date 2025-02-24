package com.example.notehub.note;

import com.example.notehub.aws.S3Service;
import com.example.notehub.dto.PagedResult;
import com.example.notehub.users.User;
import com.example.notehub.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class NoteService {

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserService userService;

    @Value("${aws.bucketName}")
    private String BUCKET_NAME;

    @Value("${aws.baseUrlTemplate}")
    private String BASE_URL_TEMPLATE;

    public Note getNote(Long noteId){
        return noteDAO.getNote(noteId);
    }

    public Note createNote(Note note, MultipartFile file){
        String fileKey = "notes/" + note.getCreatedBy() + "/" + note.getTitle();
        try {
            s3Service.uploadFile(BUCKET_NAME,fileKey,file);
        }catch (Exception e) {
            System.out.println();
        }
        String finalUrl = String.format(BASE_URL_TEMPLATE,BUCKET_NAME,fileKey);
        note.setUrl(finalUrl);
        return noteDAO.createNote(note);
    }

    public void deleteNote(Long noteId){
        noteDAO.deleteNote(noteId);
    }

    public PagedResult<Note> searchNotes(String query, List<Long> subjectIds, Long page){
        return noteDAO.searchNotes(query,subjectIds,page);
    }

    public List<Note> getMyNotes(){
        String userName = userService.getCurrentUsername();
        User user = userService.getUserByUserName(userName);
        return noteDAO.getNotes(user.getUserId());
    }
}
