# LeetCode Problems 21-25: Visual Flows and Java Implementation

## 21. Word Break

<table>
<tr>
<td width="50%">

```mermaid
graph TD
    A[Start] --> B[Initialize DP array]
    B --> C["Set dp[0] = true"]
    C --> D[Process each position]
    D --> E[Check each substring]
    E --> F{Word in dictionary?}
    F -->|Yes| G["Update dp[j]"]
    F -->|No| H[Continue]
    H --> E
    G --> E
    E --> D
    D --> I["Return dp[n]"]
```

</td>
<td width="50%">

```java
public boolean wordBreak(String s, 
                        List<String> wordDict) {
    Set<String> dictionary = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && dictionary.contains(
                s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    
    return dp[s.length()];
}
```

</td>
</tr>
</table>

## 22. Trapping Rain Water

<table>
<tr>
<td width="50%">

```mermaid
graph TD
    A[Start] --> B[Initialize pointers]
    B --> C[Initialize maxLeft, maxRight]
    C --> D{left <= right?}
    D -->|Yes| E{"height[left] <= height[right]?"}
    E -->|Yes| F[Update maxLeft]
    E -->|No| G[Update maxRight]
    F --> H[Add water]
    G --> I[Add water]
    H --> J[Move left]
    I --> K[Move right]
    J --> D
    K --> D
    D -->|No| L[Return total]
```

</td>
<td width="50%">

```java
public int trap(int[] height) {
    if (height == null || height.length < 3)
        return 0;
        
    int left = 0, right = height.length - 1;
    int maxLeft = 0, maxRight = 0;
    int water = 0;
    
    while (left <= right) {
        if (height[left] <= height[right]) {
            if (height[left] >= maxLeft) {
                maxLeft = height[left];
            } else {
                water += maxLeft - height[left];
            }
            left++;
        } else {
            if (height[right] >= maxRight) {
                maxRight = height[right];
            } else {
                water += maxRight - height[right];
            }
            right--;
        }
    }
    
    return water;
}
```

</td>
</tr>
</table>

## 23. Rotate Image

<table>
<tr>
<td width="50%">

```mermaid
graph TD
    A[Start] --> B[Transpose matrix]
    B --> C[Reverse each row]
    C --> D[Process each row]
    D --> E[Swap elements]
    E --> D
    D --> F[Next row]
    F --> C
    C --> G[Done]
```

</td>
<td width="50%">

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;
    
    // Transpose
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
    
    // Reverse each row
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n/2; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[i][n-1-j];
            matrix[i][n-1-j] = temp;
        }
    }
}
```

</td>
</tr>
</table>

## 24. Group Anagrams

<table>
<tr>
<td width="50%">

```mermaid
graph TD
    A[Start] --> B[Create HashMap]
    B --> C[Process each string]
    C --> D[Get character count]
    D --> E[Create key]
    E --> F[Add to group]
    F --> C
    C --> G[Return values]
```

</td>
<td width="50%">

```java
public List<List<String>> groupAnagrams(
    String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String str : strs) {
        char[] chars = new char[26];
        for (char c : str.toCharArray()) {
            chars[c - 'a']++;
        }
        String key = new String(chars);
        
        map.computeIfAbsent(key, 
            k -> new ArrayList<>()).add(str);
    }
    
    return new ArrayList<>(map.values());
}
```

</td>
</tr>
</table>

## 25. Merge Intervals

<table>
<tr>
<td width="50%">

```mermaid
graph TD
    A[Start] --> B[Sort intervals]
    B --> C[Initialize result]
    C --> D[Process each interval]
    D --> E{Overlaps with previous?}
    E -->|Yes| F[Merge intervals]
    E -->|No| G[Add new interval]
    F --> D
    G --> D
    D --> H[Return result]
```

</td>
<td width="50%">

```java
public int[][] merge(int[][] intervals) {
    if (intervals == null || 
        intervals.length <= 1) 
        return intervals;
        
    // Sort by start time
    Arrays.sort(intervals, 
        (a, b) -> Integer.compare(a[0], b[0]));
    
    List<int[]> result = new ArrayList<>();
    int[] current = intervals[0];
    result.add(current);
    
    for (int[] interval : intervals) {
        if (current[1] >= interval[0]) {
            // Merge overlapping intervals
            current[1] = Math.max(current[1], 
                                interval[1]);
        } else {
            // Add non-overlapping interval
            current = interval;
            result.add(current);
        }
    }
    
    return result.toArray(
        new int[result.size()][]);
}
```

</td>
</tr>
</table>

Each problem includes:
- Detailed flow diagram showing the algorithm steps
- Clean, well-formatted Java implementation
- Visual representation of the process
- Proper error handling and edge cases

Key features:
1. Word Break uses dynamic programming
2. Trapping Rain Water uses two-pointer technique
3. Rotate Image uses matrix manipulation
4. Group Anagrams uses HashMap with character counting
5. Merge Intervals uses sorting and merging

Would you like me to:
1. Add complexity analysis for any problem?
2. Provide alternative solutions?
3. Add more detailed comments?
4. Expand any flow diagram?