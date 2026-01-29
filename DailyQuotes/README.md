# 每日名言 (DailyQuotes)

一个简洁优雅的 iOS 每日名言应用，使用 SwiftUI 构建。

## 功能特性

- 📅 **今日名言** - 每天自动展示一条精选名言
- 📚 **名言库** - 浏览所有名言，支持分类筛选
- ❤️ **收藏功能** - 收藏喜欢的名言
- 📤 **分享功能** - 一键分享名言到社交平台
- 🎨 **精美UI** - 渐变背景、卡片动画、流畅交互

## 项目结构

```
DailyQuotes/
├── DailyQuotes.xcodeproj/     # Xcode 项目文件
└── DailyQuotes/
    ├── DailyQuotesApp.swift   # App 入口
    ├── Views/                  # 视图层
    │   ├── ContentView.swift      # 主界面（TabView）
    │   ├── TodayView.swift        # 今日名言页
    │   ├── QuoteListView.swift    # 名言库页
    │   ├── QuoteCardView.swift    # 名言卡片组件
    │   └── FavoriteView.swift     # 收藏页
    ├── Models/                 # 数据模型
    │   ├── Quote.swift            # 名言数据结构
    │   └── QuoteData.swift        # 名言数据管理
    ├── Assets.xcassets/        # 资源文件
    └── Preview Content/        # 预览资源
```

## 如何在自己的 iPhone 上运行

### 前提条件

1. **Mac 电脑** - 需要 macOS 系统
2. **Xcode 15+** - 从 App Store 免费下载
3. **Apple ID** - 用于签名应用

### 步骤

#### 1. 用 Xcode 打开项目

```bash
open DailyQuotes.xcodeproj
```

或者双击 `DailyQuotes.xcodeproj` 文件。

#### 2. 配置开发者账号

1. 打开 Xcode，点击菜单 **Xcode → Settings** (或按 `⌘,`)
2. 选择 **Accounts** 标签页
3. 点击左下角 **+** 按钮，选择 **Apple ID**
4. 登录你的 Apple ID

#### 3. 配置项目签名

1. 在 Xcode 左侧选择项目 **DailyQuotes**
2. 在中间面板选择 **Signing & Capabilities** 标签
3. 勾选 **Automatically manage signing**
4. 在 **Team** 下拉菜单中选择你的 Apple ID
5. 如果 Bundle Identifier 显示红色错误，修改为唯一的标识符：
   - 例如：`com.你的名字.DailyQuotes`

#### 4. 连接 iPhone

1. 用数据线连接 iPhone 到 Mac
2. iPhone 上弹出"信任此电脑"，点击"信任"
3. 在 Xcode 顶部工具栏，选择你的 iPhone 作为运行目标

#### 5. 信任开发者证书（首次运行）

1. 第一次运行时，iPhone 上会提示"不受信任的开发者"
2. 在 iPhone 上打开 **设置 → 通用 → VPN与设备管理**
3. 找到你的 Apple ID，点击并选择"信任"

#### 6. 运行应用

1. 点击 Xcode 左上角的 **▶️ 运行按钮**（或按 `⌘R`）
2. 等待编译完成，应用会自动安装到 iPhone 并启动

### 常见问题

**Q: 提示 "Unable to install"**
A: 检查 iPhone 是否已解锁，确保 USB 连接正常

**Q: 提示签名错误**
A: 确保选择了正确的 Team，Bundle Identifier 是唯一的

**Q: 应用7天后无法打开**
A: 免费开发者账号签名的应用有效期7天，重新用 Xcode 运行即可续期

## 技术栈

- **SwiftUI** - 声明式 UI 框架
- **Swift 5** - 编程语言
- **iOS 16+** - 最低支持版本

## 名言分类

- 励志 🔥
- 智慧 💡
- 人生 🌈
- 成功 🏆
- 友谊 🤝

## 未来计划

- [ ] 添加更多名言
- [ ] 支持用户自定义添加名言
- [ ] 添加每日提醒通知
- [ ] 支持 Widget 小组件
- [ ] 数据持久化（CoreData/UserDefaults）

## 许可证

MIT License
