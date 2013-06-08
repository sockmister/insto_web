class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :user
      t.integer :points

      t.timestamps
    end
  end
end
