package com.hashtag.SamplePokerApp;

public class Card {
	
	private int suitCard;
	
	private int rankValueCard;
	
	private String cardStringValue;
	
	public Card(String rankSuit) throws Exception {
		
		if(isValidCard(rankSuit)) {
			
			this.cardStringValue = rankSuit;
			this.suitCard = parseSuitCard(rankSuit.substring(rankSuit.length()-1));
			this.rankValueCard = parseRankCard(rankSuit.substring(0,rankSuit.length()-1));
		}
	}
	
	public String getCardStringValue() {
		return cardStringValue;
	}
	
	public int getRankValueCard() {
		return rankValueCard;
	}
	
	public int getSuitCard() {
		return suitCard;
	}
	
	private boolean isValidCard(String cardString) {
		
		if(cardString == null)
		{
			return false;
		}
		if(cardString.length() > 3 || cardString.length() < 2)
		{
			return false;
		}
		
		String suit = cardString.substring(cardString.length()-1);
		String rank = cardString.substring(0,cardString.length()-1);
		
		if(!isValidSuit(suit))
		{
			return false;
		}
		else if(!isValidRank(rank))
		{
			return false;
		}
		
		return true;
	}
	
	private static boolean isValidSuit(String suit)
	{
		if(suit.equalsIgnoreCase("S") || suit.equalsIgnoreCase("H") || suit.equalsIgnoreCase("C") 
				|| suit.equalsIgnoreCase("D"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean isValidRank(String rank)
	{
		if(rank.equalsIgnoreCase("A") || rank.equalsIgnoreCase("K") || rank.equalsIgnoreCase("Q") 
				|| rank.equalsIgnoreCase("J") || rank.equalsIgnoreCase("10") || rank.equalsIgnoreCase("9")
				|| rank.equalsIgnoreCase("8") || rank.equalsIgnoreCase("7") || rank.equalsIgnoreCase("6")
				|| rank.equalsIgnoreCase("5") || rank.equalsIgnoreCase("4") || rank.equalsIgnoreCase("3")
				|| rank.equalsIgnoreCase("2"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int parseSuitCard(String suit)
	{
		switch(suit) 
		{
		case "S":
			return 4;
		case "H":
			return 3;
		case "C":
			return 2;
		case "D":
		default:
			return 1;
		}
	}
	
	private int parseRankCard(String rank)
	{
		switch(rank) 
		{
		case "A":
			return 14;
		case "K":
			return 13;
		case "Q":
			return 12;
		case "J":
			return 11;
		case "10":
			return 10;
		case "9":
			return 9;
		case "8":
			return 8;
		case "7":
			return 7;
		case "6":
			return 6;
		case "5":
			return 5;
		case "4":
			return 4;
		case "3":
			return 3;
		case "2":
		default:
			return 2;
		}
	}
}
