import java.util.List;

public class TestLoc8rrrr {
	public static void main(String[] args) {
		Loc8erDB l = new Loc8erDB();
		List<Location> list = l.determine8("hotel", -97.740136869788, 30.281551016594);
		for(Location ll : list) {
			System.out.println(ll.getName() + "\t" + ll.getXpos() + "\t" + ll.getYpos());
		}
		System.out.println("\n\n\n");
		String changeLoc = "AT&T Executive Education and Conference Center";
		for(Location ll : l.getBusiness().get("hotel")) {
			if(ll.getName().equals(changeLoc)) {
				ll.setRating(-1);
			}
		}
		
		List<Location> listt = l.determine8("hotel", -97.740136869788, 30.281551016594);
		for(Location ll : listt) {
			System.out.println(ll.getName() + "\t" + ll.getXpos() + "\t" + ll.getYpos());
		}
		
	}
}
