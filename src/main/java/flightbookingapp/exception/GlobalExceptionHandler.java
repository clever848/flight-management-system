package flightbookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import flightbookingapp.dto.Response;
import flightbookingapp.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<String>> handleNFE(NotFoundException e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMsg(e.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidException.class)
	public ResponseEntity<Response<String>> handleIE(InvalidException e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMsg(e.getMessage());
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	//must be at last
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Response<String>> handleRTE(RuntimeException e)
	{
		Response<String> res = new Response<String>();
		res.setData(null);
		res.setMsg(e.getMessage());
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
}
