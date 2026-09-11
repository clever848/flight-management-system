package flightbookingapp.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
	private T data;
	private String msg;
	private int statusCode;
	
	public Response<T> fetchResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMsg("fetched successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public Response<T> saveResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMsg("created successfully");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	public Response<T> updateResponse(T data)
	{
		Response<T> res = new Response<T>();
		res.setData(data);
		res.setMsg("updated successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public Response<T> deleteResponse()
	{
		Response<T> res = new Response<T>();
		res.setData(null);
		res.setMsg("deleted successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
}
