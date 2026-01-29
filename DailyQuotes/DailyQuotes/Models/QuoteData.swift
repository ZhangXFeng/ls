//
//  QuoteData.swift
//  DailyQuotes
//
//  名言数据源
//

import Foundation

class QuoteData: ObservableObject {
    @Published var quotes: [Quote] = []
    @Published var todayQuote: Quote?
    @Published var favoriteQuotes: [Quote] = []

    init() {
        loadQuotes()
        selectTodayQuote()
    }

    // 加载名言数据
    private func loadQuotes() {
        quotes = [
            // 励志名言
            Quote(content: "千里之行，始于足下。", author: "老子", category: "励志"),
            Quote(content: "业精于勤，荒于嬉；行成于思，毁于随。", author: "韩愈", category: "励志"),
            Quote(content: "天行健，君子以自强不息。", author: "《周易》", category: "励志"),
            Quote(content: "不积跬步，无以至千里；不积小流，无以成江海。", author: "荀子", category: "励志"),
            Quote(content: "锲而舍之，朽木不折；锲而不舍，金石可镂。", author: "荀子", category: "励志"),

            // 智慧名言
            Quote(content: "知之为知之，不知为不知，是知也。", author: "孔子", category: "智慧"),
            Quote(content: "学而不思则罔，思而不学则殆。", author: "孔子", category: "智慧"),
            Quote(content: "三人行，必有我师焉。", author: "孔子", category: "智慧"),
            Quote(content: "温故而知新，可以为师矣。", author: "孔子", category: "智慧"),
            Quote(content: "吾生也有涯，而知也无涯。", author: "庄子", category: "智慧"),

            // 人生名言
            Quote(content: "人生自古谁无死，留取丹心照汗青。", author: "文天祥", category: "人生"),
            Quote(content: "生当作人杰，死亦为鬼雄。", author: "李清照", category: "人生"),
            Quote(content: "路漫漫其修远兮，吾将上下而求索。", author: "屈原", category: "人生"),
            Quote(content: "人生得意须尽欢，莫使金樽空对月。", author: "李白", category: "人生"),
            Quote(content: "会当凌绝顶，一览众山小。", author: "杜甫", category: "人生"),

            // 成功名言
            Quote(content: "宝剑锋从磨砺出，梅花香自苦寒来。", author: "俗语", category: "成功"),
            Quote(content: "欲穷千里目，更上一层楼。", author: "王之涣", category: "成功"),
            Quote(content: "长风破浪会有时，直挂云帆济沧海。", author: "李白", category: "成功"),
            Quote(content: "天生我材必有用，千金散尽还复来。", author: "李白", category: "成功"),
            Quote(content: "莫愁前路无知己，天下谁人不识君。", author: "高适", category: "成功"),

            // 友谊名言
            Quote(content: "海内存知己，天涯若比邻。", author: "王勃", category: "友谊"),
            Quote(content: "桃花潭水深千尺，不及汪伦送我情。", author: "李白", category: "友谊"),
            Quote(content: "劝君更尽一杯酒，西出阳关无故人。", author: "王维", category: "友谊"),
            Quote(content: "人生交契无老少，论交何必先同调。", author: "杜甫", category: "友谊"),
            Quote(content: "同是天涯沦落人，相逢何必曾相识。", author: "白居易", category: "友谊"),

            // 国外名言
            Quote(content: "生活不是等待暴风雨过去，而是学会在雨中跳舞。", author: "维维安·格林", category: "人生"),
            Quote(content: "成功不是最终的，失败也不是致命的，重要的是继续前进的勇气。", author: "丘吉尔", category: "成功"),
            Quote(content: "你的时间有限，不要浪费在过别人的生活上。", author: "史蒂夫·乔布斯", category: "励志"),
            Quote(content: "活着就是为了改变世界，难道还有其他原因吗？", author: "史蒂夫·乔布斯", category: "励志"),
            Quote(content: "我思故我在。", author: "笛卡尔", category: "智慧"),
        ]
    }

    // 选择今日名言（基于日期）
    func selectTodayQuote() {
        let calendar = Calendar.current
        let dayOfYear = calendar.ordinality(of: .day, in: .year, for: Date()) ?? 1
        let index = (dayOfYear - 1) % quotes.count
        todayQuote = quotes[index]
    }

    // 获取随机名言
    func getRandomQuote() -> Quote {
        quotes.randomElement() ?? quotes[0]
    }

    // 按分类筛选名言
    func getQuotes(by category: String) -> [Quote] {
        quotes.filter { $0.category == category }
    }

    // 收藏/取消收藏
    func toggleFavorite(_ quote: Quote) {
        if let index = favoriteQuotes.firstIndex(where: { $0.id == quote.id }) {
            favoriteQuotes.remove(at: index)
        } else {
            favoriteQuotes.append(quote)
        }
    }

    // 检查是否已收藏
    func isFavorite(_ quote: Quote) -> Bool {
        favoriteQuotes.contains(where: { $0.id == quote.id })
    }
}
