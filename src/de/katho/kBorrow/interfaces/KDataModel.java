package de.katho.kBorrow.interfaces;

import java.util.ArrayList;

public interface KDataModel {
	public void register(KGuiModel pModel);
	public void updateModel();
	public ArrayList<?> getData();
	public Object get(int id);
}
