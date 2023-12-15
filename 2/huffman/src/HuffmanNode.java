import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        left = right = null;
    }

    public int compareTo(HuffmanNode node) {
        return frequency - node.frequency;
    }
}

