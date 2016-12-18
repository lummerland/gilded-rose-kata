import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GildedRoseTest {

	private GildedRose shop;

	@Before
	public void setUp() throws Exception {
		shop = new GildedRose();
	}

	@Test
	public void testThatAnItemCanBeAddedAndRetrieved() {
		final Item sulfuras = new Item(GildedRose.AGED_BRIE, 0, 80);
		shop.addItem(sulfuras);
		final Item item = shop.getItem(0);
		assertThat(item, is(sulfuras));
	}

	@Test
	public void testThatSulfurasDoesNotChangeAfterOneDay() {
		assertQualityAfterDays(new Item(GildedRose.SULFURAS_HAND_OF_RAGNAROS, 0, 80), 1, 80);
	}

	@Test
	public void testThatSulfurasDoesNotChangeAfterTwoDays() {
		assertQualityAfterDays(new Item(GildedRose.SULFURAS_HAND_OF_RAGNAROS, 0, 80), 2, 80);
	}

	@Test
	public void testThatBrieIncreasesQuality() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.AGED_BRIE, 2, 0), 1, 1);
	}

	@Test
	public void testThatQualityDecreasesFasterAfterSellDate() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.ELIXIR_OF_THE_MONGOOSE, 0, 4), 1, 2);
	}

	@Test
	public void testThatQualityIsNeverNegative() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.ELIXIR_OF_THE_MONGOOSE, 1, 0), 1, 0);
	}

	@Test
	public void testThatQualityNeverIsBiggerThan50() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.AGED_BRIE, 1, 50), 1, 50);
	}

	@Test
	public void testBackstagePassesMoreThan10DaysLeft() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 15, 20), 1, 21);
	}

	@Test
	public void testBackstagePasses10DaysLeft() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 10, 20), 1, 22);
	}

	@Test
	public void testBackstagePasses9DaysLeft() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 9, 20), 1, 22);
	}

	@Test
	public void testBackstagePasses5DaysOrLessLeft() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 5, 10), 1, 13);
	}

	@Test
	public void testBackstagePasses1DaysLeftShouldIncreaseQualityBy3() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 1, 10), 1, 13);
	}

	@Test
	public void testBackstagePassesLoseAllTheirValueAfterTheConcert() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.BACKSTAGE_PASSES, 0, 10), 1, 0);
	}

	@Test
	public void testThatBrieIncreasesQualityOverMultipleDays() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.AGED_BRIE, 5, 1), 2, 3);
	}

	@Test
	public void testThatSellInDecreasesOverTime() {
		Item item = new Item(GildedRose.AGED_BRIE, 5, 1);
		shop.addItem(item);
		shop.updateQuality();
		assertThat(shop.getItem(0).getSellIn(), is(4));

		shop.updateQuality();
		assertThat(shop.getItem(0).getSellIn(), is(3));
	}

	@Test
	public void testThatSellInOfSulfurasDoesNotChangeOverTime() {
		Item item = new Item(GildedRose.SULFURAS_HAND_OF_RAGNAROS, 5, 1);
		shop.addItem(item);
		shop.updateQuality();
		assertThat(shop.getItem(0).getSellIn(), is(5));

		shop.updateQuality();
		assertThat(shop.getItem(0).getSellIn(), is(5));
	}

	@Test
	public void testThatQualityDecreasesTwiceAsFastForConjuredItems() throws Exception {
		assertQualityAfterDays(new Item(GildedRose.CONJURED_MANA_CAKE, 5, 10), 1, 8);
	}

	public void assertQualityAfterDays(final Item item, int days, final int expectedQuality) {
		shop.addItem(item);
		for (int i = 0; i < days; i++)
			shop.updateQuality();
		final Item storedItem = shop.getItem(0);
		assertThat(storedItem.getQuality(), is(expectedQuality));
	}

	// TODO: Entweder A: weitere Tests für Conjured Items schreiben, da hier noch Verhalten fehlt
	//				  B: Unterschiedliche Items durch Produkte realisieren, Conjured Erbt von normalen Item
	// TODO: Test Experimente: junit java 8 lambdas? Lesbarere assertQualityAfterDays Methode (Welcher Parameter macht was?)
}
