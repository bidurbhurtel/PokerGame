package com.hashtag.SamplePokerApp;

import java.io.Serializable;

public class Hand implements Serializable{

	private static final long serialVersionUID = -2146262003746850570L;

    private String name;
    
    private String[] hand;
    
    private Card[] pokerHand;
    
	private int pokerHandLargestRank;
	
	private final int lowCardIndex = 0;
	
	private final int highCardIndex = 4;
	
	public boolean initializeHand()
	{
		try{
			pokerHand = new Card[hand.length];
			pokerHandLargestRank = 0;
			for(int i = 0; i < pokerHand.length; i++)
			{
				pokerHand[i] = new Card(hand[i]);
			}
			
			this.sort();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public String getName() {
        return name;
    }
    
    public void setName(String name) throws Exception {
    	if(this.name == null)
    	{
    		this.name = name;
    	}
    }
    
    public String[] getHand() {
        return hand;
    }
    
    public void setHand(String[] hand) throws Exception {
    	if(this.hand ==  null)
    	{
	        this.hand = hand;
	    }
    }
	
	public void setPokerHandLargestRank(int value)
	{
		this.pokerHandLargestRank = value;
	}
	
	public int getPokerHandLargestRank()
	{
		return pokerHandLargestRank;
	}
	
	public Card[] getPokerHand()
	{
		return pokerHand;
	}
	
	public int getSmallestCardRank()
	{
		return pokerHand[lowCardIndex].getRankValueCard();
	}
	
	public int getLargestCardRank()
	{
		return pokerHand[highCardIndex].getRankValueCard();
	}
	
	private void sort()
	{
		for(int i = 1; i < this.pokerHand.length; i++)
		{
			Card curCard = pokerHand[i];
			int curIndex = i;
			while( curIndex > 0 && pokerHand[curIndex -1].getRankValueCard() > curCard.getRankValueCard())
			{
				pokerHand[curIndex] = pokerHand[curIndex-1];
				curIndex--;
			}
			pokerHand[curIndex] = curCard;
		}
	}
	
	// toString
	public String toString()
	{
		String output = this.name + " : ";
		for(Card c : pokerHand)
		{
			output += c.getCardStringValue() + ", ";
		}
		
		return output;
	}

}
