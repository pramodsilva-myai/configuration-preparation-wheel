# IntelliJ IDEA Optimization Summary Table

| Category | Optimization Technique | Steps | Effect |
|----------|------------------------|-------|--------|
| Memory Management | Increase IDE Memory Heap Size | 1. Help > Change Memory Settings<br>2. Increase Maximum Memory<br>3. Restart IntelliJ | Improves performance for larger projects |
| | Adjust Garbage Collector | Edit `idea64.vmoptions` file, add:<br>`-XX:+UseG1GC`<br>`-XX:SoftRefLRUPolicyMSPerMB=50`<br>`-XX:+HeapDumpOnOutOfMemoryError`<br>`-XX:+UseStringDeduplication`<br>`-XX:+AlwaysPreTouch` | Enhances memory management |
| Plugin Management | Disable Unused Plugins | File > Settings > Plugins<br>Disable unnecessary plugins | Reduces IDE load |
| Performance Modes | Configure Power Save Mode | Click battery icon > Power Save Mode | Reduces background activities |
| Indexing | Exclude Unnecessary Files | 1. File > Settings > Editor > File Types<br>2. Add to "Ignore files and folders"<br>3. Mark directories as excluded in Project View | Speeds up indexing |
| | Use Shared Indexes | File > Settings > Shared Indexes<br>Enable download of shared indexes | Accelerates project indexing |
| File Management | Enable Automatic File Caching | File > Settings > System Settings > Synchronization<br>Enable "Save files automatically if application is idle" | Reduces unnecessary disk operations |
| CPU Utilization | Allocate More CPU Cores | Add to VM options:<br>`-Djava.util.concurrent.ForkJoinPool.common.parallelism=<num_cores>` | Speeds up background tasks |
| Code Inspection | Disable Unused Inspections | File > Settings > Editor > Inspections<br>Uncheck unnecessary inspections | Reduces CPU usage for analysis |
| UI Optimization | Optimize IDE Appearance | File > Settings > Appearance & Behavior<br>Turn off animations and visual effects | Improves responsiveness |
| Terminal | Optimize Terminal Performance | File > Settings > Tools > Terminal<br>Disable "Shell integration" | Speeds up embedded terminal |
| Cache Management | Invalidate Caches / Restart | File > Invalidate Caches / Restart | Refreshes indexes and clears outdated caches |
| Project Configuration | Exclude Build Outputs | Use `.gitignore` to exclude build outputs | Reduces indexing load |
| | Configure Module Dependencies | Set appropriate module dependencies | Improves build and indexing speed |
| Version Control | Optimize Git Settings | Use local Git repository<br>Adjust Git settings for large projects | Enhances VCS performance |
| Editor Settings | Limit Code Folding | Disable code folding if unused | Improves editor responsiveness |
| Build Configuration | Use Build Tools Efficiently | Enable Gradle or Maven daemon<br>Enable parallel builds | Speeds up build process |
| Hardware Optimization | Use SSD for Project Files | Move project files to SSD | Improves overall responsiveness |
| | Ensure Adequate RAM | Upgrade to 16GB+ RAM | Enhances overall performance |
| OS-specific | Windows: Exclude from Antivirus | Add IntelliJ and project directories to antivirus exclusions | Prevents scanning slowdowns |
| | macOS: Manage Spotlight Indexing | Exclude project folders from Spotlight | Reduces background indexing |
| | Linux: Adjust File Watcher Limits | Increase file watcher limits | Improves file system responsiveness |
