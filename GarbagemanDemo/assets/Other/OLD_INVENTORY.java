//uses old for loop method
public void upInv(){//update the inventory display when an item is changed
	showInfo = false;
	infoItem = null;
	int size = 128;
	int startX = 260;
	int yPos = game.window_height-290;
	int yPlus = size+15;
	int xPos = startX;
	int xPlus = size+30;
	int tot = 0;
	inv.add(noContent);
	//System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB"+game.backpack.contents.size());
	//System.out.println("infoLabels: "+infoLabels.size());
	//System.out.println("make labels: "+(infoLabels.size()==0));
	if (infoLabels.size()== 0){
		createLabels();
	}
	for (int u = 0; u < game.backpack.contents.size(); u++)
		//System.out.println(u+": "+game.backpack.contents.get(u).name);
		try {
			if (game.backpack.contents.size() > 0 && !noContent.equals(null)) {
				//System.out.println("contents: "+ game.backpack.contents.size());
				noContent.setVisible(false);
				// System.out.println("set vis false: "+noContent.isVisible());
				for (int y = 0; y <= 3; y++) {
					//System.out.println("looped");
					for (int x = 0; x < 5; x++) {
						//System.out.println("tot: "+tot+"  "+"size: "+game.backpack.contents.size());
						if (tot < game.backpack.contents.size()) {
							//System.out.println("here we are");
							final Trash item = game.backpack.contents.get(tot);
							boolean canAdd = game.passTrash.findTrash(item);
							if (!canAdd) {
								//System.out.println("interval " + tot + ";;;; " + item.name);

								ImageButton.ImageButtonStyle ibStyle = new ImageButton.ImageButtonStyle();
								ibStyle.imageUp = item.getDrawable();

								ibStyle.imageUp.setMinHeight(size);
								ibStyle.imageUp.setMinWidth(size);
								final ImageButton localB = new ImageButton(ibStyle);
								localB.setBounds(xPos + 25, yPos, size, size);
								localB.setSize(squareSize, squareSize);

								//System.out.println("INFO: "+item.name+", "+item.rarity);


								final int finalTot = tot;
								localB.addListener(new InputListener() {
									public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
										if (currentDown == localB) {
											currentDown = null;
											infoItem = null;
											showInfo = false;

										} else if (currentDown == null || currentDown != localB) {
											showInfo = true;
											infoX = (int) localB.getX();
											infoY = (int) localB.getY();
											currentDown = localB;

											infoItem = item;
											infoItemIndex = finalTot;
										}

										return true;
									}

									;
								});
								inv.add(localB);
								localB.setVisible(false);
								stage.addActor(localB);
								if (!canAdd)
									xPos = xPos + xPlus;
								else
									xPos = xPos - xPlus;
								//System.out.println(x);

								//System.out.println(x + " "+(x <= 6));

							}
							tot++;

							canAdd = false;
						}
					}
					yPos = yPos - yPlus;
					xPos = startX;
				}
				curInv = game.backpack.contents;
				//System.out.println("DONE");
			} else if (game.backpack.contents.size() == 0) {
				System.out.println("THERE IS NO INV STUFF");
				noContent.setVisible(true);
				//game.backpack.add(new McdHamburger());
			}
		}
		catch (java.lang.NullPointerException j){
			j.printStackTrace();
		}
}