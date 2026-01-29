//
//  QuoteListView.swift
//  DailyQuotes
//
//  名言列表页面
//

import SwiftUI

struct QuoteListView: View {
    @EnvironmentObject var quoteData: QuoteData
    @State private var selectedCategory: String = "全部"

    private let categories = ["全部", "励志", "智慧", "人生", "成功", "友谊"]

    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                // 分类选择器
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 12) {
                        ForEach(categories, id: \.self) { category in
                            CategoryButton(
                                title: category,
                                isSelected: selectedCategory == category
                            ) {
                                withAnimation(.easeInOut(duration: 0.2)) {
                                    selectedCategory = category
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.vertical, 12)
                }
                .background(Color(.systemBackground))

                // 名言列表
                ScrollView {
                    LazyVStack(spacing: 16) {
                        ForEach(filteredQuotes) { quote in
                            QuoteCardView(quote: quote)
                                .padding(.horizontal)
                                .contextMenu {
                                    Button(action: {
                                        quoteData.toggleFavorite(quote)
                                    }) {
                                        Label(
                                            quoteData.isFavorite(quote) ? "取消收藏" : "收藏",
                                            systemImage: quoteData.isFavorite(quote) ? "heart.slash" : "heart"
                                        )
                                    }

                                    Button(action: {
                                        UIPasteboard.general.string = "\(quote.content)\n—— \(quote.author)"
                                    }) {
                                        Label("复制", systemImage: "doc.on.doc")
                                    }
                                }
                        }
                    }
                    .padding(.vertical)
                }
            }
            .navigationTitle("名言库")
            .background(Color(.systemGroupedBackground))
        }
    }

    private var filteredQuotes: [Quote] {
        if selectedCategory == "全部" {
            return quoteData.quotes
        }
        return quoteData.getQuotes(by: selectedCategory)
    }
}

// 分类按钮组件
struct CategoryButton: View {
    let title: String
    let isSelected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.subheadline)
                .fontWeight(isSelected ? .semibold : .regular)
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(
                    Capsule()
                        .fill(isSelected ? Color.orange : Color(.systemGray5))
                )
                .foregroundColor(isSelected ? .white : .primary)
        }
    }
}

#Preview {
    QuoteListView()
        .environmentObject(QuoteData())
}
