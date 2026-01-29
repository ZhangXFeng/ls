//
//  FavoriteView.swift
//  DailyQuotes
//
//  收藏页面
//

import SwiftUI

struct FavoriteView: View {
    @EnvironmentObject var quoteData: QuoteData

    var body: some View {
        NavigationView {
            Group {
                if quoteData.favoriteQuotes.isEmpty {
                    // 空状态
                    VStack(spacing: 20) {
                        Image(systemName: "heart.slash")
                            .font(.system(size: 60))
                            .foregroundColor(.gray.opacity(0.5))

                        Text("暂无收藏")
                            .font(.title2)
                            .foregroundColor(.secondary)

                        Text("点击名言卡片上的 ❤️ 按钮收藏喜欢的名言")
                            .font(.subheadline)
                            .foregroundColor(.gray)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal, 40)
                    }
                } else {
                    // 收藏列表
                    ScrollView {
                        LazyVStack(spacing: 16) {
                            ForEach(quoteData.favoriteQuotes) { quote in
                                QuoteCardView(quote: quote)
                                    .padding(.horizontal)
                                    .contextMenu {
                                        Button(role: .destructive, action: {
                                            withAnimation {
                                                quoteData.toggleFavorite(quote)
                                            }
                                        }) {
                                            Label("取消收藏", systemImage: "heart.slash")
                                        }

                                        Button(action: {
                                            UIPasteboard.general.string = "\(quote.content)\n—— \(quote.author)"
                                        }) {
                                            Label("复制", systemImage: "doc.on.doc")
                                        }
                                    }
                                    .transition(.asymmetric(
                                        insertion: .scale.combined(with: .opacity),
                                        removal: .slide.combined(with: .opacity)
                                    ))
                            }
                        }
                        .padding(.vertical)
                    }
                }
            }
            .navigationTitle("我的收藏")
            .toolbar {
                if !quoteData.favoriteQuotes.isEmpty {
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Text("\(quoteData.favoriteQuotes.count) 条")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                }
            }
            .background(Color(.systemGroupedBackground))
        }
    }
}

#Preview {
    FavoriteView()
        .environmentObject(QuoteData())
}
