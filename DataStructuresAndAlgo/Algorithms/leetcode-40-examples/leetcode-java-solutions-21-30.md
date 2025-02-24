# LeetCode Problems 21-30 - Java Solutions with Visualizations

## 21. Merge Two Sorted Lists
**Difficulty**: Easy  
**Time**: O(n + m) | **Space**: O(1)

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }
    
    current.next = l1 != null ? l1 : l2;
    return dummy.next;
}
```

```
Visualization:
Input: 
L1: 1->2->4
L2: 1->3->4

Process:
Step 1: 1->1->
Step 2: 1->1->2->
Step 3: 1->1->2->3->
Step 4: 1->1->2->3->4->4

Output: 1->1->2->3->4->4
```

## 22. Generate Parentheses
**Difficulty**: Medium  
**Time**: O(4^n/√n) | **Space**: O(n)

```java
public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, "", 0, 0, n);
    return result;
}

private void backtrack(List<String> result, String current, 
                      int open, int close, int max) {
    if (current.length() == max * 2) {
        result.add(current);
        return;
    }
    
    if (open < max) {
        backtrack(result, current + "(", open + 1, close, max);
    }
    if (close < open) {
        backtrack(result, current + ")", open, close + 1, max);
    }
}
```

```
Visualization:
Input: n = 2

                ""
              /    \
            (      X
           /  \
         ((    ()
          |     \
         (()    ()(
          |      |
         (())   ()()

Output: ["(())", "()()"]
```

## 23. Merge k Sorted Lists
**Difficulty**: Hard  
**Time**: O(N log k) | **Space**: O(k)

```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    
    PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
    
    // Add first node from each list
    for (ListNode list : lists) {
        if (list != null) {
            queue.offer(list);
        }
    }
    
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (!queue.isEmpty()) {
        ListNode node = queue.poll();
        current.next = node;
        current = current.next;
        
        if (node.next != null) {
            queue.offer(node.next);
        }
    }
    
    return dummy.next;
}
```

```
Visualization:
Input: 
1->4->5
1->3->4
2->6

MinHeap:
Step 1: [1,1,2]
Step 2: [1,2,3]
Step 3: [2,3,4]
Step 4: [3,4,4]
Step 5: [4,4,5]
Step 6: [4,5,6]

Output: 1->1->2->3->4->4->5->6
```

## 24. Swap Nodes in Pairs
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(1)

```java
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode current = dummy;
    
    while (current.next != null && current.next.next != null) {
        ListNode first = current.next;
        ListNode second = current.next.next;
        
        first.next = second.next;
        second.next = first;
        current.next = second;
        
        current = current.next.next;
    }
    
    return dummy.next;
}
```

```
Visualization:
Input: 1->2->3->4

Step 1: 0->1->2->3->4
Step 2: 0->2->1->3->4
Step 3: 0->2->1->4->3

Output: 2->1->4->3
```

## 25. Reverse Nodes in k-Group
**Difficulty**: Hard  
**Time**: O(n) | **Space**: O(1)

```java
public ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || k == 1) return head;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    ListNode curr = head;
    ListNode next = null;
    
    int count = 0;
    while (curr != null) {
        count++;
        curr = curr.next;
    }
    
    while (count >= k) {
        curr = prev.next;
        next = curr.next;
        
        for (int i = 1; i < k; i++) {
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
            next = curr.next;
        }
        
        prev = curr;
        count -= k;
    }
    
    return dummy.next;
}
```

```
Visualization:
Input: 1->2->3->4->5, k = 2

Step 1: 1->2->3->4->5
Step 2: 2->1->3->4->5
Step 3: 2->1->4->3->5

Output: 2->1->4->3->5
```

## 26. Remove Duplicates from Sorted Array
**Difficulty**: Easy  
**Time**: O(n) | **Space**: O(1)

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int i = 0;
    for (int j = 1; j < nums.length; j++) {
        if (nums[j] != nums[i]) {
            i++;
            nums[i] = nums[j];
        }
    }
    
    return i + 1;
}
```

```
Visualization:
Input: [1,1,2,2,3]

Step 1: [1,1,2,2,3] i=0, j=1
Step 2: [1,2,2,2,3] i=1, j=2
Step 3: [1,2,3,2,3] i=2, j=4

Output: [1,2,3] (length = 3)
```

## 27. Remove Element
**Difficulty**: Easy  
**Time**: O(n) | **Space**: O(1)

```java
public int removeElement(int[] nums, int val) {
    int i = 0;
    for (int j = 0; j < nums.length; j++) {
        if (nums[j] != val) {
            nums[i] = nums[j];
            i++;
        }
    }
    return i;
}
```

```
Visualization:
Input: [3,2,2,3], val = 3

Step 1: [3,2,2,3] i=0, j=0
Step 2: [2,2,2,3] i=1, j=1
Step 3: [2,2,2,3] i=2, j=2

Output: [2,2] (length = 2)
```

## 28. Find the Index of the First Occurrence in a String
**Difficulty**: Easy  
**Time**: O(n*m) | **Space**: O(1)

```java
public int strStr(String haystack, String needle) {
    if (needle.isEmpty()) return 0;
    
    for (int i = 0; i <= haystack.length() - needle.length(); i++) {
        int j;
        for (j = 0; j < needle.length(); j++) {
            if (haystack.charAt(i + j) != needle.charAt(j)) {
                break;
            }
        }
        if (j == needle.length()) {
            return i;
        }
    }
    
    return -1;
}
```

```
Visualization:
Input: haystack = "hello", needle = "ll"

Step 1: Check "he" - No match
Step 2: Check "el" - No match
Step 3: Check "ll" - Match found! ✓
        h e l l o
          | | |
          l l

Output: 2
```

## 29. Divide Two Integers
**Difficulty**: Medium  
**Time**: O(log n) | **Space**: O(1)

```java
public int divide(int dividend, int divisor) {
    if (dividend == Integer.MIN_VALUE && divisor == -1) {
        return Integer.MAX_VALUE;
    }
    
    int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;
    long dvd = Math.abs((long) dividend);
    long dvs = Math.abs((long) divisor);
    int result = 0;
    
    while (dvd >= dvs) {
        long temp = dvs;
        long multiple = 1;
        
        while (dvd >= (temp << 1)) {
            temp <<= 1;
            multiple <<= 1;
        }
        
        dvd -= temp;
        result += multiple;
    }
    
    return sign * result;
}
```

```
Visualization:
Input: dividend = 10, divisor = 3

Process:
10 - 3 = 7   (count = 1)
7 - 3 = 4    (count = 2)
4 - 3 = 1    (count = 3)
1 < 3        (stop)

Output: 3
```

## 30. Substring with Concatenation of All Words
**Difficulty**: Hard  
**Time**: O(n*m*k) | **Space**: O(m*k)

```java
public List<Integer> findSubstring(String s, String[] words) {
    List<Integer> result = new ArrayList<>();
    if (s == null || words == null || words.length == 0) return result;
    
    Map<String, Integer> wordCount = new HashMap<>();
    for (String word : words) {
        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
    }
    
    int wordLen = words[0].length();
    int totalLen = wordLen * words.length;
    
    for (int i = 0; i <= s.length() - totalLen; i++) {
        Map<String, Integer> seenWords = new HashMap<>();
        int j;
        
        for (j = 0; j < words.length; j++) {
            int startIndex = i + j * wordLen;
            String currentWord = s.substring(startIndex, startIndex + wordLen);
            
            if (!wordCount.containsKey(currentWord)) break;
            
            seenWords.put(currentWord, 
                         seenWords.getOrDefault(currentWord, 0) + 1);
            
            if (seenWords.get(currentWord) > 
                wordCount.getOrDefault(currentWord, 0)) break;
        }
        
        if (j == words.length) {
            result.add(i);
        }
    }
    
    return result;
}
```

```
Visualization:
Input: 
s = "barfoothefoobarman"
words = ["foo","bar"]

Process:
Step 1: Check "barfoo"
        bar ✓
        foo ✓
        Add index 0

Step 2: Check "foobar"
        foo ✓
        bar ✓
        Add index 9

Output: [0,9]
```

Each solution includes:
- Complete Java implementation
- Time and space complexity
- Visual representation of the algorithm
- Step-by-step process visualization
- Example input/output
