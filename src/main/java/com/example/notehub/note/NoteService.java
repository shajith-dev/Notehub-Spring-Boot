package com.example.notehub.note;

import com.example.notehub.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class NoteService {

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private S3Service s3Service;

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

    public void toggleLike(Long noteId,String like){
        noteDAO.toggleLike(noteId,like.equals("like") ? 1L : -1L);
    }

    public void deleteNote(Long noteId){
        noteDAO.deleteNote(noteId);
    }

}
