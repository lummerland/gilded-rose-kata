import java.util.ArrayList;
import java.util.List;

public class GildedRose {

	public static final String AGED_BRIE = "Aged Brie";
	public static final String DEXTERITY_VEST = "+5 Dexterity Vest";
	public static final String ELIXIR_OF_THE_MONGOOSE = "Elixir of the Mongoose";
	public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
	public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";

	private List<Item> items = new ArrayList<Item>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final GildedRose shop = new GildedRose();
		System.out.println("OMGHAI!");

		shop.addItem(new Item(DEXTERITY_VEST, 10, 20));
		shop.addItem(new Item(AGED_BRIE, 2, 0));
		shop.addItem(new Item(ELIXIR_OF_THE_MONGOOSE, 5, 7));
		shop.addItem(new Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80));
		shop.addItem(new Item(BACKSTAGE_PASSES, 15, 20));
		shop.addItem(new Item(CONJURED_MANA_CAKE, 3, 6));

		shop.updateQuality();
	}

	void updateQuality() {
		for (Item item : items) {
			if (SULFURAS_HAND_OF_RAGNAROS.equals(item.getName())) {
				continue;
			}

			// Besser werdende Produkte
			if ((AGED_BRIE.equals(item.getName())) || BACKSTAGE_PASSES.equals(item.getName())) {
				if (item.getQuality() < 50) {
					item.setQuality(item.getQuality() + 1);

					if (BACKSTAGE_PASSES.equals(item.getName())) {
						if (item.getSellIn() < 11) {
							increaseQuality(item);
						}

						if (item.getSellIn() < 6) {
							increaseQuality(item);
						}
					}
				}
			} else {
				if (item.getQuality() > 0) {
					item.setQuality(item.getQuality() - 1);
				}
			}

			item.setSellIn(item.getSellIn() - 1);

			if (item.getSellIn() < 0) {
				if (AGED_BRIE.equals(item.getName())) {
					increaseQuality(item);
				} else if (BACKSTAGE_PASSES.equals(item.getName())) {
					item.setQuality(0);
				} else {
					if (item.getQuality() > 0) {
						item.setQuality(item.getQuality() - 1);
					}
				}
			}
		}
	}

	private void increaseQuality(Item item) {
		if (item.getQuality() < 50) {
            item.setQuality(item.getQuality() + 1);
        }
	}

	boolean addItem(final Item e) {
		return items.add(e);
	}

	public Item getItem(final int index) {
		return items.get(index);
	}
}
