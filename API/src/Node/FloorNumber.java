package Node;

public enum FloorNumber {
    FLOOR_LTWO("L2", 1),
    FLOOR_LONE("L1",2),
    FLOOR_GROUND("G",3),
    FLOOR_ONE("1",4),
    FLOOR_TWO("2",5),
    FLOOR_THREE("3",6);
    private String dbMapping;
    private int nodeMapping;

    FloorNumber(String dbMapping, int nodeMapping) {
        this.dbMapping = dbMapping;
        this.nodeMapping = nodeMapping;
    }

    public static Node.FloorNumber fromDbMapping(String dbMapping) {
        for (Node.FloorNumber floorNumber : Node.FloorNumber.values()) {
            if (floorNumber.dbMapping.equals(dbMapping)) {
                return floorNumber;
            }
        }
        return null;
    }

    //Getters
    public String getDbMapping() {
        return dbMapping;
    }
    public int getNodeMapping() {
        return nodeMapping;
    }
}