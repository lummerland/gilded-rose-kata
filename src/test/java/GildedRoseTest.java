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
		final Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
		shop.addItem(sulfuras);
		final Item item = shop.getItem(0);
		assertThat(item, is(sulfuras));
	}

	@Test
	public void testThatSulfurasDoesNotChangeAfterOneDay() {
		assertQualityAfterOneDay(new Item("Sulfuras, Hand of Ragnaros", 0, 80), 80);
	}

	@Test
	public void testThatBrieIncreasesQuality() throws Exception {
		assertQualityAfterOneDay(new Item("Aged Brie", 2, 0), 1);
	}

	@Test
	public void testThatQualityDecreasesFasterAfterSellDate() throws Exception {
		assertQualityAfterOneDay(new Item("Elixir of the Mongoose", 0, 4), 2);
	}

	@Test
	public void testThatQualityIsNeverNegative() throws Exception {
		assertQualityAfterOneDay(new Item("Elixir of the Mongoose", 1, 0), 0);
	}

	@Test
	public void testThatQualityNeverIsBiggerThan50() throws Exception {
		assertQualityAfterOneDay(new Item("Aged Brie", 1, 50), 50);
	}

	@Test
	public void testBackstagePassesMoreThan10DaysLeft() throws Exception {
		assertQualityAfterOneDay(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20), 21);
	}

	public void assertQualityAfterOneDay(final Item item, final int expectedQuality) {
		shop.addItem(item);
		shop.updateQuality();
		final Item storedItem = shop.getItem(0);
		assertThat(storedItem.getQuality(), is(expectedQuality));
	}

	// TODO: weitere Testfälle für die Backstage Pässe
	// TODO: Tests für die Zeit
	// TODO: kosmetisches Refactoring für bequemeres Coden
	// TODO: eigentliches Refactoring
}
