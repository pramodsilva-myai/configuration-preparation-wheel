# LeetCode Problems 11-20 - Java Solutions with Visualizations

## 11. Container With Most Water
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(1)

```java
public int maxArea(int[] height) {
    int maxArea = 0;
    int left = 0;
    int right = height.length - 1;
    
    while (left < right) {
        int width = right - left;
        maxArea = Math.max(maxArea, width * Math.min(height[left], height[right]));
        
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}
```

```
Visualization:
height = [1,8,6,2,5,4,8,3,7]

Step 1:        |     |
               |     |
           |   |     |   |
       |   |   |     |   |
   |   |   |   |   | |   |
width = 8, area = 8 * 7 = 56

Final Result: 49 (between the two height 8 bars)
```

## 12. Integer to Roman
**Difficulty**: Medium  
**Time**: O(1) | **Space**: O(1)

```java
public String intToRoman(int num) {
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
    StringBuilder result = new StringBuilder();
    
    for (int i = 0; i < values.length; i++) {
        while (num >= values[i]) {
            num -= values[i];
            result.append(symbols[i]);
        }
    }
    
    return result.toString();
}
```

```
Visualization:
Input: 1994

Process:
1994 ≥ 1000: M  (994 remaining)
994 ≥ 900:  CM  (94 remaining)
94 ≥ 90:    XC  (4 remaining)
4 ≥ 4:      IV  (0 remaining)

Output: "MCMXCIV"
```

## 13. Roman to Integer
**Difficulty**: Easy  
**Time**: O(n) | **Space**: O(1)

```java
public int romanToInt(String s) {
    Map<Character, Integer> map = new HashMap<>();
    map.put('I', 1);
    map.put('V', 5);
    map.put('X', 10);
    map.put('L', 50);
    map.put('C', 100);
    map.put('D', 500);
    map.put('M', 1000);
    
    int result = 0;
    
    for (int i = 0; i < s.length(); i++) {
        if (i < s.length() - 1 && 
            map.get(s.charAt(i)) < map.get(s.charAt(i + 1))) {
            result -= map.get(s.charAt(i));
        } else {
            result += map.get(s.charAt(i));
        }
    }
    
    return result;
}
```

```
Visualization:
Input: "MCMXCIV"

Process:
M = 1000  (+1000)
C = 100   (-100)  [CM = 900]
M = 1000  (+1000)
X = 10    (-10)   [XC = 90]
C = 100   (+100)
I = 1     (-1)    [IV = 4]
V = 5     (+5)

Output: 1994
```

## 14. Longest Common Prefix
**Difficulty**: Easy  
**Time**: O(S) where S is sum of all characters | **Space**: O(1)

```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }
    }
    
    return prefix;
}
```

```
Visualization:
Input: ["flower", "flow", "flight"]

Step 1: prefix = "flower"
Step 2: "flow".indexOf("flower") != 0
        prefix = "flowe"
        prefix = "flow"
Step 3: "flight".indexOf("flow") != 0
        prefix = "flo"
        prefix = "fl"

Output: "fl"
```

## 15. 3Sum
**Difficulty**: Medium  
**Time**: O(n²) | **Space**: O(1)

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue;
        
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left+1]) left++;
                while (left < right && nums[right] == nums[right-1]) right--;
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return result;
}
```

```
Visualization:
Input: [-1,0,1,2,-1,-4]
Sorted: [-4,-1,-1,0,1,2]

Process:
Fix: -4
    Left: -1, Right: 2  (sum = -3)
Fix: -1
    Left: -1, Right: 2  (sum = 0) ✓
    Left: 0, Right: 1   (sum = 0) ✓

Output: [[-1,-1,2], [-1,0,1]]
```

## 16. 3Sum Closest
**Difficulty**: Medium  
**Time**: O(n²) | **Space**: O(1)

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closestSum = nums[0] + nums[1] + nums[2];
    
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int currentSum = nums[i] + nums[left] + nums[right];
            
            if (currentSum == target) return target;
            
            if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                closestSum = currentSum;
            }
            
            if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return closestSum;
}
```

```
Visualization:
Input: nums = [-1,2,1,-4], target = 1

Sorted: [-4,-1,1,2]

Process:
Fix: -4
    Sum = -4 + (-1) + 2 = -3
Fix: -1
    Sum = -1 + 1 + 2 = 2 ✓ (closest to target)

Output: 2
```

## 17. Letter Combinations of a Phone Number
**Difficulty**: Medium  
**Time**: O(4^n) | **Space**: O(n)

```java
public List<String> letterCombinations(String digits) {
    if (digits == null || digits.length() == 0) return new ArrayList<>();
    
    String[] mapping = {
        "", "", "abc", "def", "ghi", "jkl",
        "mno", "pqrs", "tuv", "wxyz"
    };
    
    List<String> result = new ArrayList<>();
    backtrack(result, digits, "", 0, mapping);
    return result;
}

private void backtrack(List<String> result, String digits, 
                      String current, int index, String[] mapping) {
    if (index == digits.length()) {
        result.add(current);
        return;
    }
    
    String letters = mapping[digits.charAt(index) - '0'];
    for (char letter : letters.toCharArray()) {
        backtrack(result, digits, current + letter, index + 1, mapping);
    }
}
```

```
Visualization:
Input: "23"

     ""
   /  |  \
  a   b   c    (2)
 /|\  /|\ /|\
d e f d e f d e f  (3)

Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

## 18. 4Sum
**Difficulty**: Medium  
**Time**: O(n³) | **Space**: O(1)

```java
public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums == null || nums.length < 4) return result;
    
    Arrays.sort(nums);
    
    for (int i = 0; i < nums.length - 3; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue;
        
        for (int j = i + 1; j < nums.length - 2; j++) {
            if (j > i + 1 && nums[j] == nums[j-1]) continue;
            
            int left = j + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[j], 
                                           nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left+1]) left++;
                    while (left < right && nums[right] == nums[right-1]) right--;
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
    }
    
    return result;
}
```

```
Visualization:
Input: nums = [1,0,-1,0,-2,2], target = 0

Sorted: [-2,-1,0,0,1,2]

Process:
Fix: -2, -1
    Left: 0, Right: 2  (sum = -1)
Fix: -2, 0
    Left: 0, Right: 2  (sum = 0) ✓
Fix: -1, 0
    Left: 0, Right: 2  (sum = 1)

Output: [[-2,0,0,2], [-1,0,0,1]]
```

## 19. Remove Nth Node From End of List
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(1)

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode first = dummy;
    ListNode second = dummy;
    
    // Advance first pointer by n+1 steps
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both pointers until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove nth node
    second.next = second.next.next;
    
    return dummy.next;
}
```

```
Visualization:
Input: 1->2->3->4->5, n = 2

Step 1: dummy->1->2->3->4->5
        f           s

Step 2: dummy->1->2->3->4->5
                    s     f

Step 3: dummy->1->2->3->5
                    s   

Output: 1->2->3->5
```

## 20. Valid Parentheses
**Difficulty**: Easy  
**Time**: O(n) | **Space**: O(n)

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
        }
    }
    
    return stack.isEmpty();
}
```

```
Visualization:
Input: "{[]}"

Stack operations:
1. Push {   Stack: [{]
2. Push [   Stack: [{, []
3. Push ]   Stack: [{]
4. Push }   Stack: []

Stack is empty -> Valid

Input: "([)]"
1. Push (   Stack: [(]
2. Push [   Stack: [(, []
3. Push )   Stack: [(] -> Invalid
```

Each solution includes:
- Complete Java implementation
- Time and space complexity
- Visual representation of the algorithm
- Step-by-step process visualization
- Example input/output
