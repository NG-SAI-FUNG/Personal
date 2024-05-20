package blog.example.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long blogId;
	
	@NonNull
	private String blogImage;
	
	@NonNull
	private String blogTitle;
	
	@NonNull
	private String categoryName;
	
	@NonNull
	private String message;
	
	@NonNull
	@Temporal(TemporalType.DATE)
	private Date blogDate;
	
	@NonNull
	private String authorImage;
	
	@NonNull
	private String authorMessage;
	
	private Long userId;

	
}
