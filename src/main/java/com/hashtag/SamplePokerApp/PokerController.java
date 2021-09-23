package com.hashtag.SamplePokerApp;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ControllerAdvice
@RestController
public class PokerController {

	private static final String pokerUri = "/poker";
	private static final String win = "%s wins!";
	private static final String draw = "Draw. No one wins!";
	private static final String clientProblem = "We have a problem! %s";
	@RequestMapping(value = pokerUri, method = RequestMethod.POST)
    @ResponseBody
    public Result comparePokerhand(@RequestBody @Valid Game game) 
    {
    	try{
    		if(game == null)
    		{
    			throw new Exception("We are missing something!");
    		}
    		else if(game.getHands() == null)
    		{
    			throw new Exception("We are missing something!");
    		}
    		else if(game.getHands().length != 2)
    		{
    			throw new Exception("We are missing someone!");
    		}
    		
	    	Hand leftHand = game.getLeftHand();
	    	Hand rightHand = game.getRightHand();
	    	if(leftHand == null || rightHand == null)
	    	{
	    		throw new Exception("We are missing something!");
	    	}
	    	else if(!leftHand.initializeHand() || !rightHand.initializeHand())
	    	{
	    		throw new Exception("Some wrong cards we got here");
	    	}

	    	// Validate the input passed to this service
	    	String validateGameResult = PokerhandHelper.validateGame(game);
	    	if(!validateGameResult.equals(PokerhandHelper.ok))
	    	{
	    		throw new Exception(String.format(clientProblem, validateGameResult));
	    	}
	    	
			int result = PokerhandHelper.compareHand(leftHand, rightHand);
	
			if(result > 0)
			{
				String s = String.format(win, rightHand.getName());
				return new Result(s);
			}
			else if(result < 0)
			{
				String s = String.format(win, leftHand.getName());
				return new Result(s);
			}
			else
			{
				return new Result(draw);
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return null;
    	
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result processClientError(Exception ex) {
        return new Result(ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Result processServerError(Exception ex) {
        return new Result(ex.getMessage());
    }

}
