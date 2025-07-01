package uce.project.com.baraja;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSongBody {
    private String prompt;
    private Integer userId;
}
