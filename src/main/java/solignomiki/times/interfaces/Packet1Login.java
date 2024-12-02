package solignomiki.times.interfaces;

public interface Packet1Login {
	void setSpringLength(int length);
	void setSummerLength(int length);
	void setFallLength(int length);
	void setWinterLength(int length);
	int getSpringLength();
	int getSummerLength();
	int getFallLength();
	int getWinterLength();
}
