package com.hashtag.SamplePokerApp;

public class PokerhandHelper {

private static final int handLen = 5;
	
	public static final String ok = "OK";
	
	private static byte[][] cardMap;

	public static String validateGame(Game game)
	{
		cardMap = new byte[14][4];
		
		if(game.getHands().length > 2)
    	{
    		return "We can only have two players!";
    	}
    	else if(game.getHands().length < 2)
    	{
    		return "We not enough players!";
    	}
		
		Hand[] hands = game.getHands();
		
		if(hands[0].getName().trim().equals(""))
    	{
    		return "Player 1 has an empty name! Comon, give him/her a name.";
    	}else if(hands[1].getName().trim().equals(""))
    	{
    		return "Player 2 has an empty name! Comon, give him/her a name.";
    	}
    	else if(hands[0].getName().equals(hands[1].getName()))
    	{
    		return "The two players has the same name! Comon, give them a unique name.";
    	}
		
		for(Hand h : hands)
		{
			if(h.getPokerHand().length > 5)
			{
				return String.format("%s has more than 5 cards!", h.getName());
			}
			else if(h.getPokerHand().length < 5)
			{
				return String.format("%s has less than 5 cards!", h.getName());
			}
			if(cardUsed(h))
			{
				return "The game has been rigged! There is a duplicate card somewhere!";
			}
		}
		
		return ok;
	}
	
	private static boolean cardUsed(Hand h)
	{
		for(Card c : h.getPokerHand())
		{
			int rank = c.getRankValueCard() -1;
			int suit = c.getSuitCard() -1;
			if(cardMap[rank][suit] == 0)
			{
				cardMap[rank][suit] = 1;  // indicate the card has been dealt
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	public static short compareHand(Hand leftHand, Hand rightHand)
	{
		PokerHandType leftPokerHand = analyzeHand(leftHand);
		PokerHandType rightPokerHand = analyzeHand(rightHand);
		
		//
		if(leftPokerHand.getPokerHandValue() ==  rightPokerHand.getPokerHandValue())
		{
			if(leftHand.getPokerHandLargestRank() > rightHand.getPokerHandLargestRank())
			{
				return -1;
			}
			else if (leftHand.getPokerHandLargestRank() < rightHand.getPokerHandLargestRank())
			{
				return 1;
			}
			else
			{
				Card[] leftHandCards = leftHand.getPokerHand();
				Card[] rightHandCards = rightHand.getPokerHand();
				for(int i = handLen-2; i > 0; i--)
				{
					if(leftHandCards[i].getRankValueCard() > rightHandCards[i].getRankValueCard())
					{
						return -1;
					}
					else if(leftHandCards[i].getRankValueCard() < rightHandCards[i].getRankValueCard())
					{
						return 1;
					}
				}
				return 0;
			}
		}
		else if (leftPokerHand.getPokerHandValue() > rightPokerHand.getPokerHandValue())
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	
	public static PokerHandType analyzeHand(Hand hand)
	{
		if(allSameSuit(hand))
		{
			int isStraight = allStraight(hand);
			if( isStraight > 0)
			{
				hand.setPokerHandLargestRank(isStraight);
				return PokerHandType.StraightFlush;
			}
			
			hand.setPokerHandLargestRank(hand.getLargestCardRank());
			return PokerHandType.Flush;
		}
		
		int isStraight = allStraight(hand);
		if( isStraight > 0)
		{
			hand.setPokerHandLargestRank(isStraight);
			return PokerHandType.Straight;
		}
		
		boolean hasPair = false;
		boolean hasTwoPair = false;
		boolean hasThreeOfAKind = false;
		boolean hasFourOfAKind = false;
		
		int largestRank = 0;
		
		Card[] pokerhand = hand.getPokerHand();
		for(int i = 0; i < handLen-1; i++)
		{
			if(pokerhand[i].getRankValueCard() == pokerhand[i+1].getRankValueCard())
			{
				if(!hasPair && largestRank == 0)
				{
					if(largestRank == 0)
					{
						largestRank = pokerhand[i].getRankValueCard();
						hasPair = true;
					}
				}
				else if (!hasPair && largestRank != pokerhand[i].getRankValueCard())
				{
					hasPair = true;
				}
				else if (hasPair)
				{
					if (largestRank != pokerhand[i+1].getRankValueCard())
					{
						hasPair = false;
						largestRank = pokerhand[i+1].getRankValueCard();
						hasTwoPair = true;
					}
					else if (!hasThreeOfAKind)
					{
						hasPair = false;
						hasThreeOfAKind = true;
					}
				}
				else if(hasTwoPair)
				{
					hasPair = true;
					hasTwoPair = false;
					hasThreeOfAKind = true;
				}
				else if (hasThreeOfAKind && !hasFourOfAKind)
				{
					hasThreeOfAKind = false;
					hasFourOfAKind = true;
				}
			}
		}
		
		if(largestRank > 0)
		{
			hand.setPokerHandLargestRank(largestRank);
			if(hasFourOfAKind)
			{
				return PokerHandType.FourOfAKind;
			}
			else if(hasThreeOfAKind)
			{
				if(hasPair)
				{
					return PokerHandType.FullHouse;
				}
				return PokerHandType.ThreeOfAKind;
			}
			else if(hasTwoPair)
			{
				return PokerHandType.TwoPair;
			}
			else
			{
				return PokerHandType.Pair;
			}
		}
		else
		{
			hand.setPokerHandLargestRank(hand.getLargestCardRank());
			return PokerHandType.HighCard;
		}
	}
	
	private static boolean allSameSuit(Hand hand)
	{
		Card[] pokerhand = hand.getPokerHand();
		int suit = pokerhand[0].getSuitCard();
		for(int i = 1; i < handLen; i++)
		{
			if(pokerhand[i].getSuitCard() != suit)
			{
				return false;
			}
		}
		return true;
	}
	
	private static int allStraight(Hand hand)
	{
		Card[] pokerhand = hand.getPokerHand();
		int firstCard = hand.getSmallestCardRank();
		int lastCard = hand.getLargestCardRank();
		
		if(firstCard == 2 && lastCard == 14)
		{
			for(int i = 0; i < handLen-2; i++)
			{
				int difference = pokerhand[i+1].getRankValueCard() - pokerhand[i].getRankValueCard();
				if( difference != 1)
				{
					return 0;
				}
			}
			return pokerhand[3].getRankValueCard();
		}
		else{
			for(int i = 0; i < handLen-1; i++)
			{
				int difference = pokerhand[i+1].getRankValueCard() - pokerhand[i].getRankValueCard();
				if( difference != 1)
				{
					return 0;
				}
			}
			return hand.getLargestCardRank();
		}
	}
}
