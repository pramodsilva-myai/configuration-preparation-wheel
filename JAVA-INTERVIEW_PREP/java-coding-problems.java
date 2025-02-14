// 1. String Palindrome Check
public boolean isPalindrome(String str) {
    if (str == null) return false;
    str = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    int left = 0, right = str.length() - 1;
    
    while (left < right) {
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}

// 2. Find First Non-Repeated Character
public char firstNonRepeatedChar(String str) {
    Map<Character, Integer> charCount = new HashMap<>();
    
    // Count character occurrences
    for (char c : str.toCharArray()) {
        charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }
    
    // Find first character with count 1
    for (char c : str.toCharArray()) {
        if (charCount.get(c) == 1) {
            return c;
        }
    }
    return '\0';
}

// 3. Fibonacci Series
public List<Integer> fibonacci(int n) {
    List<Integer> fib = new ArrayList<>();
    if (n <= 0) return fib;
    
    fib.add(0);
    if (n == 1) return fib;
    
    fib.add(1);
    for (int i = 2; i < n; i++) {
        fib.add(fib.get(i-1) + fib.get(i-2));
    }
    return fib;
}

// 4. Check if Array Contains Duplicates
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) {
            return true;
        }
    }
    return false;
}

// 5. Reverse a Linked List
public class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    
    while (current != null) {
        ListNode next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    return prev;
}

// 6. Valid Parentheses
public boolean isValidParentheses(String s) {
    Stack<Character> stack = new Stack<>();
    
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            
            char top = stack.pop();
            if ((c == ')' && top != '(') || 
                (c == '}' && top != '{') || 
                (c == ']' && top != '[')) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}

// 7. Binary Search
public int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1;
}

// 8. Find Maximum Subarray Sum (Kadane's Algorithm)
public int maxSubArray(int[] nums) {
    int maxSoFar = nums[0];
    int maxEndingHere = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    return maxSoFar;
}
