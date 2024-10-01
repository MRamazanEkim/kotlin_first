package com.example.third

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // ViewHolder class to bind views
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTodoTitle: TextView = itemView.findViewById(R.id.tvTodoTitle)
        val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // Inflate item layout for each row
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        )
        return TodoViewHolder(itemView)
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll {todo ->
        todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // Get current Todo item
        val curTodo = todos[position]
        // Bind the Todo title to the TextView
        holder.tvTodoTitle.text = curTodo.title
        holder.cbDone.isChecked = curTodo.isChecked
        toggleStrikeThrough(holder.tvTodoTitle, curTodo.isChecked)

        holder.cbDone.setOnCheckedChangeListener{ _, isChecked ->
            toggleStrikeThrough(holder.tvTodoTitle, isChecked)
            curTodo.isChecked = !curTodo.isChecked
        }

    }

    override fun getItemCount(): Int {
        return todos.size
    }
}

