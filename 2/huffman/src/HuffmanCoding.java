import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {
    private static Map<Character, String> huffmanCodes = new HashMap<>();

    public static void main(String[] args) {
        String input = "Huffman coding is a data compression algorithm.";
        Map<Character, Integer> frequencyMap = getCharacterFrequency(input);

        HuffmanNode root = buildHuffmanTree(frequencyMap);

        generateHuffmanCodes(root, "");

        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        String encodedString = encodeString(input);
        System.out.println("\nEncoded String: " + encodedString);

        String decodedString = decodeString(encodedString, root);
        System.out.println("Decoded String: " + decodedString);
    }

    private static Map<Character, Integer> getCharacterFrequency(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (minHeap.size() > 1) {
            HuffmanNode left = minHeap.poll();
            HuffmanNode right = minHeap.poll();

            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            minHeap.offer(parent);
        }

        return minHeap.poll();
    }

    private static void generateHuffmanCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
        }

        generateHuffmanCodes(root.left, code + "0");
        generateHuffmanCodes(root.right, code + "1");
    }

    private static String encodeString(String input) {
        StringBuilder encodedString = new StringBuilder();
        for (char c : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(c));
        }
        return encodedString.toString();
    }

    private static String decodeString(String encodedString, HuffmanNode root) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode current = root;
        for (char c : encodedString.toCharArray()) {
            if (c == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decodedString.append(current.character);
                current = root;
            }
        }
        return decodedString.toString();
    }
}