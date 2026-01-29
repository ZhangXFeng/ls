//
//  QuoteCardView.swift
//  DailyQuotes
//
//  名言卡片组件
//

import SwiftUI

struct QuoteCardView: View {
    let quote: Quote
    var isLarge: Bool = false

    var body: some View {
        VStack(alignment: .leading, spacing: isLarge ? 20 : 12) {
            // 引号图标
            HStack {
                Image(systemName: "quote.opening")
                    .font(isLarge ? .title : .title3)
                    .foregroundColor(.orange.opacity(0.6))
                Spacer()
            }

            // 名言内容
            Text(quote.content)
                .font(isLarge ? .title2 : .body)
                .fontWeight(.medium)
                .lineSpacing(8)
                .foregroundColor(.primary)

            // 作者
            HStack {
                Spacer()
                Text("—— \(quote.author)")
                    .font(isLarge ? .headline : .subheadline)
                    .foregroundColor(.secondary)
            }

            // 分类标签
            HStack {
                Spacer()
                Text(quote.category)
                    .font(.caption)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 4)
                    .background(categoryColor.opacity(0.2))
                    .foregroundColor(categoryColor)
                    .cornerRadius(12)
            }
        }
        .padding(isLarge ? 24 : 16)
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color(.systemBackground))
                .shadow(color: .black.opacity(0.1), radius: 10, x: 0, y: 5)
        )
    }

    private var categoryColor: Color {
        switch quote.category {
        case "励志": return .orange
        case "智慧": return .blue
        case "人生": return .purple
        case "成功": return .green
        case "友谊": return .pink
        default: return .gray
        }
    }
}

#Preview {
    VStack(spacing: 20) {
        QuoteCardView(
            quote: Quote(content: "千里之行，始于足下。", author: "老子", category: "励志"),
            isLarge: true
        )

        QuoteCardView(
            quote: Quote(content: "学而不思则罔。", author: "孔子", category: "智慧"),
            isLarge: false
        )
    }
    .padding()
}
